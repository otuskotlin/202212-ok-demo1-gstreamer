package ru.otus.otuskotlin.player.s1

import ru.otus.otuskotlin.player.*

fun play() {
    gst_init(null, null)

    val pipeline = gst_parse_launch(
        "playbin uri=https://www.freedesktop.org/software/gstreamer-sdk/data/media/sintel_trailer-480p.webm",
        null
    )

    gst_element_set_state(pipeline, GST_STATE_PLAYING)

    val bus = gst_element_get_bus(pipeline)

    val msg = gst_bus_timed_pop_filtered(
        bus,
        GST_CLOCK_TIME_NONE,
        GST_MESSAGE_ERROR or GST_MESSAGE_EOS
    )

    if (getMessageTypeFor(msg) == GST_MESSAGE_ERROR) {
        println("An error occurred! Re-run with the GST_DEBUG=*:WARN environment variable set for more details.")
    }

    /* Free resources */
    gst_message_unref(msg)
    gst_object_unref(bus)
    gst_element_set_state(pipeline, GST_STATE_NULL)
    gst_object_unref(pipeline)
}
