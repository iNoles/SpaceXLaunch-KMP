import androidx.compose.runtime.Composable
import kotlinx.datetime.Instant

@Composable
expect fun LaunchDate(instant: Instant)

expect fun formattingDate(instant: Instant): String