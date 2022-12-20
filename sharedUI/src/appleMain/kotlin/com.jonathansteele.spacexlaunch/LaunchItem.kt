package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSTimeZone
import platform.Foundation.systemTimeZone

private val MediumDateFormatter by lazy {
    val formatter = NSDateFormatter()
    formatter.dateStyle = NSDateFormatterMediumStyle
    formatter.timeStyle = NSDateFormatterShortStyle
    formatter.timeZone = NSTimeZone.systemTimeZone
    formatter
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun LaunchItem(
    launchDocs: Launch.Doc,
    modifier: Modifier,
    iconModifier: Modifier,
    launchSelected: (person: Launch.Doc) -> Unit
) {
    Card(modifier = modifier.padding(10.dp)) {
        ListItem(
            modifier = modifier.clickable {
                launchSelected(launchDocs)
            },
            /*icon = {
                launchDocs.links.patch.small?.let {
                }
            },*/
            text = { Text(text = launchDocs.name) },
            secondaryText = { LaunchDate(nsDate = launchDocs.dateUtc.toNSDate()) },
            trailing = { Text("# ${launchDocs.flightNumber}") }
        )
    }
}

@Composable
fun LaunchDate(nsDate: NSDate) {
    Text(text = MediumDateFormatter.stringFromDate(nsDate))
}

/*@Composable
fun fetchImage(url: String): ImageBitmap? {
    var image by remember(url) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        loadFullImage(url)?.let {
            image = Image.makeFromEncoded(toByteArray(it)).toComposeImageBitmap()
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
}*/