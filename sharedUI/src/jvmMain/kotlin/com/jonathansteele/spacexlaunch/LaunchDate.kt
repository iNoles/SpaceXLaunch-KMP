package com.jonathansteele.spacexlaunch

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val MediumDateFormatter by lazy {
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withZone(ZoneId.systemDefault())
}

@Composable
actual fun LaunchDate(instant: Instant) {
    Text(text = formattingDate(instant))
}

actual fun formattingDate(instant: Instant): String {
    return MediumDateFormatter.format(instant.toJavaInstant())
}