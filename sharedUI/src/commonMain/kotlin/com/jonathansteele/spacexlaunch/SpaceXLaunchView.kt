package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.runBlocking

@Composable
fun SpaceXLaunchViewWithTheme() {
    MaterialTheme {
        SpaceXLaunchView()
    }
}

@Composable
fun SpaceXLaunchView() {
    val selectedLaunch: MutableState<Launch.Doc?> = remember { mutableStateOf(null) }
    BoxWithConstraints {
        if (maxWidth.value > 1000) {
            TwoColumnsLayout(selectedLaunch)
        } else {
            SingleColumnLayout(selectedLaunch)
        }
    }
}

@Composable
fun SingleColumnLayout(selectedLaunch: MutableState<Launch.Doc?>) {
    selectedLaunch.value?.let {
        LaunchDetailView(it, selectedLaunch)
    } ?: LaunchList(selectedLaunch)
}

@Composable
fun TwoColumnsLayout(selectedLaunch: MutableState<Launch.Doc?>) {
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.4f), contentAlignment = Alignment.Center) {
            LaunchList(selectedLaunch)
        }
        LaunchDetailView(selectedLaunch.value, selectedLaunch, false)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchList(selectedLaunch: MutableState<Launch.Doc?>) {
    val scroll = rememberScrollState()
    val tabState = remember { mutableStateOf(TabState.LATEST) }
    val launchState = SpaceXAPI.fetchAllLaunches().collectAsState(emptyList())
    Column {
        Scaffold(
            topBar = { CenterAlignedTopAppBar(title = { Text("SpaceX Launch") }) }
        ) {
            Column(modifier = Modifier.padding(it)) {
                FilterTabs(tabState, scroll)
                ListBody(tabState, launchState, selectedLaunch)
            }
        }
    }
}

@Composable
fun FilterTabs(tabState: MutableState<TabState>, scrollState: ScrollState) {
    TabRow(selectedTabIndex = TabState.values().toList().indexOf(tabState.value)) {
        TabState.values().forEach {
            Tab(
                text = { Text(it.name) },
                selected = tabState.value == it,
                onClick = {
                    tabState.value = it
                    runBlocking {
                        scrollState.scrollTo(0)
                    }
                }
            )
        }
    }
}

@Composable
fun ListBody(
    tabState: MutableState<TabState>,
    launchState: State<List<Launch.Doc>?>,
    selectedLaunch: MutableState<Launch.Doc?>,
) {
    val filter = when (tabState.value) {
        TabState.LATEST -> launchState.value?.filter {
            !it.upcoming
        }

        TabState.UPCOMING -> launchState.value?.filter {
            it.upcoming
        }
    }

    val sorted = filter?.sortedByDescending { it.flightNumber } ?: emptyList()
    LazyColumn {
        items(sorted) { launch ->
            LaunchItem(launch) {
                selectedLaunch.value = it
            }
        }
    }
}
