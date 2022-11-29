package ru.otus.otuskotlin.player.s3

import ru.otus.otuskotlin.player.*

fun play3() {
    gst_init(null, null)

    pipeline(
        "playbin uri=https://www.freedesktop.org/software/gstreamer-sdk/data/media/sintel_trailer-480p.webm",
    ) {

        gst_element_set_state(this, GST_STATE_PLAYING)

        bus {
            messages {
                if (getMessageTypeFor(this) == GST_MESSAGE_ERROR) {
                    println("An error occurred! Re-run with the GST_DEBUG=*:WARN environment variable set for more details.")
                }
            }
        }
    }
}
