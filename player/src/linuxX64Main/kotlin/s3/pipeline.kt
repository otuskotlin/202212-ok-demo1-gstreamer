package ru.otus.otuskotlin.player.s3

import kotlinx.cinterop.CPointer
import ru.otus.otuskotlin.player.*

fun pipeline(pplStr: String, block: CPointer<GstElement>.() -> Unit) {
    val pl = gst_parse_launch(pplStr, null) ?: throw Exception("Empty Pipeline")
    pl.block()
    gst_element_set_state(pl, GST_STATE_NULL)
    gst_object_unref(pl)
}
