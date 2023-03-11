package com.jonathansteele.spacexlaunch.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jonathansteele.spacexlaunch.Launch

@Composable
actual fun LoadIcon(launchDocs: Launch.Doc, modifier: Modifier) {
    launchDocs.links.patch.small?.let {
        AsyncImage(
            model = it,
            contentDescription = null,
            modifier = modifier.size(64.dp)
        )
    }
}
