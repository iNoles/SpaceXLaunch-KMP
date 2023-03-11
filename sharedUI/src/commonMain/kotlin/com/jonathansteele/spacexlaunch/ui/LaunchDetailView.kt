package com.jonathansteele.spacexlaunch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jonathansteele.spacexlaunch.Launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailView(
    launchDocs: Launch.Doc?,
    selectedLaunch: MutableState<Launch.Doc?>,
    navigationEnabled: Boolean = true
) {
    if (navigationEnabled) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            selectedLaunch.value = null
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Pop Back Navigation Stack"
                            )
                        }
                    },
                    title = { Text(launchDocs?.name ?: "") }
                )
            }
        ) {
            LaunchDetailListView(launchDocs, modifier = Modifier.padding(it))
        }
    } else {
        LaunchDetailListView(launchDocs)
    }
}
@Composable
fun LaunchDetailListView(launchDocs: Launch.Doc?, modifier: Modifier = Modifier) {
    launchDocs?.let {
        LazyColumn (
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                LaunchHeader(it)
            }

            item {
                RocketCards(launchDoc = it)
            }

            items(
                items = launchDocs.payloads,
                key = { payloads ->
                    payloads.id
                }
            ) {
                PayloadCards(payload = it)
            }
        }
    } ?: Text("Select a launch")
}

@Composable
fun RocketCards(modifier: Modifier = Modifier, launchDoc: Launch.Doc) {
    Card(modifier = modifier.padding(10.dp)) {
        Column {
            Text(text = "Rocket")
            LaunchDetailContent(title = "Model", subtitle = launchDoc.rocket.name)
            LaunchDetailContent(title = "Launch window", subtitle = launchDoc.window.toString())
            LaunchDetailContent(title = "Launch success", subtitle = launchDoc.success.toString())
            if (launchDoc.success == false) {
                val firstFailure = launchDoc.failures.first()
                LaunchDetailContent(title = "Failure Time", subtitle = firstFailure.time.toString())
                LaunchDetailContent(
                    title = "Failure Altitude",
                    subtitle = firstFailure.altitude.toString()
                )
            }
        }
    }
}

@Composable
fun PayloadCards(modifier: Modifier = Modifier, payload: Launch.Doc.Payload) {
    Card(modifier = modifier.padding(10.dp)) {
        Column {
            Text(text = "Payloads")
            LaunchDetailContent(title = "Reused", subtitle = payload.reused.toString())
            LaunchDetailContent(
                title = "Manufacturer",
                subtitle = payload.manufacturers.joinToString(",")
            )
            LaunchDetailContent(
                title = "Customer",
                subtitle = payload.customers.joinToString(",")
            )
            LaunchDetailContent(
                title = "Nationality",
                subtitle = payload.nationalities.joinToString(",")
            )
            LaunchDetailContent(title = "Orbit", subtitle = payload.orbit.toString())

            val periphrasis = payload.periapsisKm?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Periapsis", subtitle = periphrasis)

            val apoptosis = payload.apoapsisKm?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Apoapsis", subtitle = apoptosis)

            val inclination = payload.inclinationDeg?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Inclination", subtitle = inclination)

            val period = payload.periodMin?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Period", subtitle = period)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailContent(title: String, subtitle: String) {
    ListItem(
        headlineText = { Text(text = title) },
        trailingContent = { Text(text = subtitle) }
    )
}