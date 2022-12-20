package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.toJavaInstant
import org.jetbrains.skia.Image
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
}