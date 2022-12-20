package com.jonathansteele.spacexlaunch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun LaunchItem(
    launchDocs: Launch.Doc,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    launchSelected: (person: Launch.Doc) -> Unit
)