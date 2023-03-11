package com.jonathansteele.spacexlaunch.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jonathansteele.spacexlaunch.Launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchHeader(
    launchDoc: Launch.Doc,
    modifier: Modifier = Modifier,
) {
    Card(modifier.padding(16.dp)) {
        ListItem(
            leadingContent = {
                LoadIcon(launchDoc, modifier)
            },
            headlineText = { Text(text = launchDoc.name) },
            supportingText = {
                LaunchDate(instant = launchDoc.dateUtc)
                Text(launchDoc.launchpad.name)
            },
        )
    }
}
