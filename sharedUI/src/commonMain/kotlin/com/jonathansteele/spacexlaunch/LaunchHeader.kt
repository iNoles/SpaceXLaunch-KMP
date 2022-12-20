package com.jonathansteele.spacexlaunch

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun LaunchHeader(
    launchDoc: Launch.Doc?,
    modifier: Modifier = Modifier,
)