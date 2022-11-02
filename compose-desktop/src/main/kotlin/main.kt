import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.jonathansteele.spacexlaunch.Launch
import com.jonathansteele.spacexlaunch.SpaceXAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaInstant
import org.jetbrains.skia.Image.Companion.makeFromEncoded
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import javax.imageio.ImageIO

private val MediumDateFormatter by lazy {
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withZone(ZoneId.systemDefault())
}

fun main() = application {
    val scroll = rememberScrollState()
    val tabState = remember { mutableStateOf(TabState.LATEST) }
    Window(
        onCloseRequest = ::exitApplication,
        title = "SpaceX Launch",
        state = rememberWindowState()
    ) {
        val launchState = SpaceXAPI.fetchAllLaunches().collectAsState(emptyList())
        MaterialTheme {
            Scaffold(
                topBar = { TopAppBar(title = { Text("SpaceX Launch") }) }
            ) {
                Column {
                    filterLaunchList(tabState, scroll)
                    LaunchList(tabState, launchState)
                }
            }
        }
    }
}

@Composable
fun filterLaunchList(tabState: MutableState<TabState>, scrollState: ScrollState) {
    TabRow(selectedTabIndex = TabState.values().indexOf(tabState.value)) {
        TabState.values().forEach {
            Tab(
                text = { Text(it.name) },
                selected = tabState.value == it,
                onClick = {
                    tabState.value = it
                    runBlocking {
                        scrollState.scrollTo(0)
                    }
                }
            )
        }
    }
}

@Composable
fun LaunchList(
    tabState: MutableState<TabState>,
    launchState: State<List<Launch.Doc>?>,
) {
    var selectedLaunch by remember { mutableStateOf<Launch.Doc?>(null) }
    val filter = when(tabState.value) {
        TabState.LATEST -> launchState.value?.filter {
            !it.upcoming
        }
        TabState.UPCOMING ->  launchState.value?.filter {
            it.upcoming
        }
    }

    val sorted = filter?.sortedByDescending { it.flightNumber } ?: emptyList()
    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.width(250.dp).fillMaxHeight().background(color = Color.LightGray)) {
            LazyColumn {
                items(sorted) { launch ->
                    LaunchItem(launch) {
                        selectedLaunch = it
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(1.dp).fillMaxHeight())

        Box(modifier = Modifier.fillMaxHeight()) {
            selectedLaunch?.let {
                LaunchDetailView(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchItem(
    launchDocs: Launch.Doc,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    launchSelected: (person: Launch.Doc) -> Unit
) {
    Card(modifier = modifier.padding(10.dp)) {
        ListItem(
            modifier = modifier.clickable {
                launchSelected(launchDocs)
            },
            icon = {
                launchDocs.links.patch.small?.let {
                    fetchImage(it)?.let { it1 ->
                        Image(it1, contentDescription = null, modifier = iconModifier.size(64.dp))
                    }
                }
            },
            text = { Text(text = launchDocs.name) },
            secondaryText = { LaunchDate(instant = launchDocs.dateUtc.toJavaInstant()) },
            trailing = { Text("# ${launchDocs.flightNumber}") }
        )
    }
}

@Composable
fun LaunchDate(instant: java.time.Instant) {
    Text(text = MediumDateFormatter.format(instant))
}

@Composable
fun fetchImage(url: String): ImageBitmap? {
    var image by remember(url) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        loadFullImage(url)?.let {
            image = makeFromEncoded(toByteArray(it)).toComposeImageBitmap()
        }
    }

    return image
}

fun toByteArray(bitmap: BufferedImage): ByteArray {
    val baos = ByteArrayOutputStream()
    ImageIO.write(bitmap, "png", baos)
    return baos.toByteArray()
}

suspend fun loadFullImage(source: String): BufferedImage? = withContext(Dispatchers.IO) {
    runCatching {
        val url = URL(source)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.connect()

        val input: InputStream = connection.inputStream
        val bitmap: BufferedImage? = ImageIO.read(input)
        bitmap
    }.getOrNull()
}

@Composable
fun LaunchDetailView(launchDocs: Launch.Doc) {
    LazyColumn(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LaunchHeader(launchDocs)
        }

        items(launchDocs.payloads) {
            Text(text = "Payloads")
            LaunchDetailContent(title = "Reused", subtitle = it.reused.toString())
            LaunchDetailContent(title = "Manufacturer", subtitle = it.manufacturers.joinToString(","))
            LaunchDetailContent(title = "Customer", subtitle = it.customers.joinToString(","))
            LaunchDetailContent(title = "Nationality", subtitle = it.nationalities.joinToString(","))
            LaunchDetailContent(title = "Orbit", subtitle = it.orbit.toString())

            val periphrasis = it.periapsisKm?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Periapsis", subtitle = periphrasis)

            val apoptosis = it.apoapsisKm?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Apoapsis", subtitle = apoptosis)

            val inclination = it.inclinationDeg?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Inclination", subtitle = inclination)

            val period = it.periodMin?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Period", subtitle = period)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchHeader(
    launchDoc: Launch.Doc,
    iconModifier: Modifier = Modifier,
) {
    Card {
        ListItem(
            icon = {
                launchDoc.links.patch.small?.let {
                    fetchImage(it)?.let { it1 ->
                        Image(it1, contentDescription = null, modifier = iconModifier.size(64.dp))
                    }
                }
            },
            text = { Text(text = launchDoc.name) },
            secondaryText = {
                LaunchDate(instant = launchDoc.dateUtc.toJavaInstant())
                            },
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchDetailContent(title: String, subtitle: String) {
    ListItem(
        text = { Text(text = title) },
        trailing = { Text(text = subtitle) }
    )
}
