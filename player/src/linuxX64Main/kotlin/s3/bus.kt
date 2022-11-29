package ru.otus.otuskotlin.player.s3

import kotlinx.cinterop.CPointer
import ru.otus.otuskotlin.player.*

fun CPointer<GstElement>.bus(block: CPointer<GstBus>.() -> Unit) {
    val bus = gst_element_get_bus(this) ?: throw Exception("Bus is empty")
    bus.block()
    gst_object_unref(bus)

}
