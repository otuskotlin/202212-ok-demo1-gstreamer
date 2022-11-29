package ru.otus.otuskotlin.player.s2

import ru.otus.otuskotlin.player.*

@Suppress("unused")
fun getProbeType(type: GstPadProbeType): Set<String> {
    val set = mutableSetOf<String>()
    if (type == 0U) return setOf("GST_PAD_PROBE_TYPE_INVALID")
    if (GST_PAD_PROBE_TYPE_IDLE and type > 0UL) set.add("GST_PAD_PROBE_TYPE_IDLE")
    if (GST_PAD_PROBE_TYPE_BLOCK and type > 0UL) set.add("GST_PAD_PROBE_TYPE_BLOCK")

    if (GST_PAD_PROBE_TYPE_BUFFER and type > 0UL) set.add("GST_PAD_PROBE_TYPE_BUFFER")
    if (GST_PAD_PROBE_TYPE_BUFFER_LIST and type > 0UL) set.add("GST_PAD_PROBE_TYPE_BUFFER_LIST")

    if (GST_PAD_PROBE_TYPE_EVENT_DOWNSTREAM and type > 0UL) set.add("GST_PAD_PROBE_TYPE_EVENT_DOWNSTREAM")
    if (GST_PAD_PROBE_TYPE_EVENT_UPSTREAM and type > 0UL) set.add("GST_PAD_PROBE_TYPE_EVENT_UPSTREAM")
    if (GST_PAD_PROBE_TYPE_EVENT_FLUSH and type > 0UL) set.add("GST_PAD_PROBE_TYPE_EVENT_FLUSH")

    if (GST_PAD_PROBE_TYPE_QUERY_DOWNSTREAM and type > 0UL) set.add("GST_PAD_PROBE_TYPE_QUERY_DOWNSTREAM")
    if (GST_PAD_PROBE_TYPE_QUERY_UPSTREAM and type > 0UL) set.add("GST_PAD_PROBE_TYPE_QUERY_UPSTREAM")

    if (GST_PAD_PROBE_TYPE_PUSH and type > 0UL) set.add("GST_PAD_PROBE_TYPE_PUSH")
    if (GST_PAD_PROBE_TYPE_PULL and type > 0UL) set.add("GST_PAD_PROBE_TYPE_PULL")
    return set.toSet()
}
