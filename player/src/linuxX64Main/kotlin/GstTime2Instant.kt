package ru.otus.otuskotlin.player

import kotlinx.cinterop.cValue
import kotlinx.cinterop.ptr
import kotlinx.cinterop.useContents
import kotlinx.datetime.Instant
import platform.posix.timespec
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds

fun gstTime2Instant(startTime: Instant, time: GstClockTime): Instant? = if (time != GST_CLOCK_TIME_NONE) {
    cValue<timespec> { gstTime2Timespec(time, this.ptr) }
        .useContents { startTime + tv_sec.seconds + tv_nsec.nanoseconds }
} else null
