package com.jk.quiztime.data.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


fun Long.toFormattedDateTimeString() : String {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds = this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.date} ${localDateTime.time}"
}