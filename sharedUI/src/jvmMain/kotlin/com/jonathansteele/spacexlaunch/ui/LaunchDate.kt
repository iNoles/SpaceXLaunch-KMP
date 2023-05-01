package com.jonathansteele.spacexlaunch.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val _mediumDateFormatter by lazy {
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withZone(ZoneId.systemDefault())
}

@Composable
actual fun LaunchDate(instant: Instant) {
    Text(text = formattingDate(instant))
}

actual fun formattingDate(instant: Instant): String {
    return _mediumDateFormatter.format(instant.toJavaInstant())
}