package ru.otus.otuskotlin.player

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class UserData(
    val startTime: Instant = Clock.System.now(),
)
