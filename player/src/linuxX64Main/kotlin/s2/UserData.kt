package ru.otus.otuskotlin.player.s2

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class UserData(
    val startTime: Instant = Clock.System.now(),
)
