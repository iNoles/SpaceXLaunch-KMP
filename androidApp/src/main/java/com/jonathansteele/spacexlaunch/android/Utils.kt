package com.jonathansteele.spacexlaunch.android

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val MediumDateFormatter: DateTimeFormatter by lazy {
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withZone(ZoneId.systemDefault())
}

fun formattingJavaDate(instant: Instant): String =
    MediumDateFormatter.format(instant.toJavaInstant())

