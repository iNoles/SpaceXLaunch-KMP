package com.jonathansteele.spacexlaunch.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jonathansteele.spacexlaunch.Launch

@Composable
expect fun LoadIcon(launchDocs: Launch.Doc, modifier: Modifier)