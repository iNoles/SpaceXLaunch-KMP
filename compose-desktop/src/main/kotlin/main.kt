import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jonathansteele.spacexlaunch.SpaceXLaunchView

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SpaceX Launch",
        state = rememberWindowState()
    ) {
        SpaceXLaunchView()
    }
}
