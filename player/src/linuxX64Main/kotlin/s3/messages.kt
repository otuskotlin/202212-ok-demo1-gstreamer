package ru.otus.otuskotlin.player.s3

import kotlinx.cinterop.CPointer
import ru.otus.otuskotlin.player.*

fun CPointer<GstBus>.messages(block: CPointer<GstMessage>.() -> Unit) {
    val msg = gst_bus_timed_pop_filtered(
        this,
        GST_CLOCK_TIME_NONE,
        GST_MESSAGE_ERROR or GST_MESSAGE_EOS
    ) ?: throw Exception("Empty Message")

    msg.block()

    /* Free resources */
    gst_message_unref(msg)

}
