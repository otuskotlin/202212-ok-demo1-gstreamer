package ru.otus.otuskotlin.player.s2

import kotlinx.cinterop.*
import ru.otus.otuskotlin.player.*

const val ELEMENT_NAME = "element"

fun play2() {
    gst_init(null, null)

    val pipeline = gst_parse_launch(
//        "playbin uri=https://www.freedesktop.org/software/gstreamer-sdk/data/media/sintel_trailer-480p.webm",
//        """
//            videotestsrc ! videoconvert name=$ELEMENT_NAME ! autovideosink
//        """.trimIndent(),
        """
        videotestsrc ! video/x-raw, format=RGBA, width=640, height=480 ! videoconvert name=$ELEMENT_NAME ! autovideosink
        """.trimIndent(),
        null
    )
    val element = gst_bin_get_by_name(pipeline?.reinterpret(), ELEMENT_NAME)

    // List names of all pads
    gst_element_foreach_pad(
        element,
        staticCFunction { el: CPointer<GstElement>?, pad: CPointer<GstPad>?, userData: gpointer? ->
            println("PAD: ${getPadName(pad)?.toKString()}")
            1
        },
        null
    )

    // the target pad that the probe to be attached to
    val pad = gst_element_get_static_pad(element, "sink")
    val userDataGlobal = UserData()
    val userDataStableRef = StableRef.create(userDataGlobal)
    val probe = gst_pad_add_probe(
        pad,
        GST_PAD_PROBE_TYPE_BUFFER,
        staticCFunction(::handleBuffer),
        userDataStableRef.asCPointer(),
        null,
    )

    gst_element_set_state(pipeline, GST_STATE_PLAYING)

    val bus = gst_element_get_bus(pipeline)
    val msg = gst_bus_timed_pop_filtered(
        bus,
        GST_CLOCK_TIME_NONE,
        GST_MESSAGE_ERROR or GST_MESSAGE_EOS,
    )

    if (getMessageTypeFor(msg) == GST_MESSAGE_ERROR) {
        println(
            "An error occurred! Re-run with the GST_DEBUG=*:WARN environment " +
                    "variable set for more details."
        )
    }

    gst_message_unref(msg)
    gst_object_unref(bus)
    gst_pad_remove_probe(pad, probe)
    gst_object_unref(pad)
    gst_object_unref(element)
    gst_element_set_state(pipeline, GST_STATE_NULL)
    gst_object_unref(pipeline)
    userDataStableRef.dispose()
}

