import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant
import kotlinx.datetime.toJSDate

@Composable
actual fun LaunchDate(instant: Instant) {
    Text(text = formattingDate(instant))
}

actual fun formattingDate(instant: Instant): String {
    return instant.toJSDate().toLocaleString()
}