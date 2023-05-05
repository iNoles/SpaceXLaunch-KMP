import com.jonathansteele.spacexlaunch.ui.SpaceXLaunchViewWithTheme
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        browserViewportWindow("SpaceXLaunch-KMP") {
            SpaceXLaunchViewWithTheme()
        }
    }
}