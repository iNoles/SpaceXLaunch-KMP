package com.jonathansteele.spacexlaunch

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun LaunchHeader(
    launchDoc: Launch.Doc,
    modifier: Modifier,
) {
    Card {
        ListItem(
            /*icon = {
                launchDoc?.links?.patch?.small?.let {
                    fetchImage(it)?.let { it1 ->
                        Image(it1, contentDescription = null, modifier = modifier.size(64.dp))
                    }
                }
            },*/
            headlineText = { Text(text = launchDoc.name) },
            supportingText = {
                LaunchDate(instant = launchDoc.dateUtc)

            },
        )
    }
}