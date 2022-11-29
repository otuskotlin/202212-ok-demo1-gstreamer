package ru.otus.otuskotlin.player.s2

import kotlinx.cinterop.*
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.player.*

fun handleBuffer(pd: CPointer<GstPad>?, probeInfo: CPointer<GstPadProbeInfo>?, userData: gpointer?): GstPadProbeReturn {
    if (probeInfo == null) {
        println("EMPTY probeInfo")
        return GstPadProbeReturn.GST_PAD_PROBE_OK
    }
//            println("PROBE_INFO_TYPE: ${getProbeType(probeInfo.pointed.type).joinToString()}")
    if (probeInfo.pointed.type and GST_PAD_PROBE_TYPE_BUFFER == 0U) {
        println("Wrong probeInfo type: ${probeInfo.pointed.type}")
        return GstPadProbeReturn.GST_PAD_PROBE_OK
    }
    val buffer = gst_pad_probe_info_get_buffer(probeInfo)
    if (buffer == null) {
        println("EMPTY buffer")
        return GstPadProbeReturn.GST_PAD_PROBE_OK
    }
    val ud = userData?.asStableRef<UserData>()?.get() ?: throw Exception("StartData is NULL")
    val startTime = ud.startTime
    val dts: Instant? = gstTime2Instant(startTime, buffer.pointed.dts)
    val pts: Instant? = gstTime2Instant(startTime, buffer.pointed.pts)
    println("DTS: $dts  // $pts")

    val caps = gst_pad_get_current_caps(pd) ?: throw Exception("No Caps in pad found")
    val capsStr = gst_caps_to_string(caps)?.toKString()
    val structure = gst_caps_get_structure(caps, 0)
    val capsWith = strFieldValue<Int>(structure, "width")
    val capsHeight = strFieldValue<Int>(structure, "height")

    gst_caps_unref(caps)

    cValue<GstMapInfo> {
        if (gst_buffer_map(buffer, this.ptr, GST_MAP_READ) == 1) {
            val imageSize = size.toInt()

//            @Suppress("UNUSED_VARIABLE")
            // Raw Image as ByteArray
//            val image = data?.readBytes(imageSize) ?: throw Exception("Empty Data")

            println("Buffer $capsStr\n${capsWith}x${capsHeight} size: $imageSize")
            gst_buffer_unmap(buffer, this.ptr)
        } else {
            println("Cannot map buffer")
        }
    }
    return GstPadProbeReturn.GST_PAD_PROBE_OK
}

