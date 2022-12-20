package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.toJavaInstant

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun LaunchHeader(
    launchDoc: Launch.Doc?,
    modifier: Modifier,
) {
    Card {
        ListItem(
            icon = {
                launchDoc?.links?.patch?.small?.let {
                    fetchImage(it)?.let { it1 ->
                        Image(it1, contentDescription = null, modifier = modifier.size(64.dp))
                    }
                }
            },
            text = { launchDoc?.name?.let { Text(text = it) } },
            secondaryText = {
                launchDoc?.dateUtc?.toJavaInstant()?.let { LaunchDate(instant = it) }
            },
        )
    }
}