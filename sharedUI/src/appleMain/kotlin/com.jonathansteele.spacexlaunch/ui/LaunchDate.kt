package com.jonathansteele.spacexlaunch.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSTimeZone
import platform.Foundation.systemTimeZone

private val _mediumDateFormatter by lazy {
    val formatter = NSDateFormatter()
    formatter.dateStyle = NSDateFormatterMediumStyle
    formatter.timeStyle = NSDateFormatterShortStyle
    formatter.timeZone = NSTimeZone.systemTimeZone
    formatter
}

@Composable
actual fun LaunchDate(instant: Instant) {
    Text(text = formattingDate(instant))
}

actual fun formattingDate(instant: Instant): String {
    return _mediumDateFormatter.stringFromDate(instant.toNSDate())
}
