package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
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
            headlineText = { Text(text = launchDocs.name) },
            supportingText = { LaunchDate(instant = launchDocs.dateUtc) },
            trailingContent = { Text("# ${launchDocs.flightNumber}") }
        )
    }
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