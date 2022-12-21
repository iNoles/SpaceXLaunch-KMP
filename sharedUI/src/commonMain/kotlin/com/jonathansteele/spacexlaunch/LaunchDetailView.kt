package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LaunchDetailView(launchDocs: Launch.Doc?) {
    LazyColumn(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LaunchHeader(launchDocs)
        }

        item {
            RocketCards(launchDoc = launchDocs)
        }

        items(
            items = launchDocs?.payloads ?: emptyList(),
            key = { payloads ->
                payloads.id
            }
        ) {
            PayloadCards(payload = it)
        }
    }
}

@Composable
fun RocketCards(modifier: Modifier = Modifier, launchDoc: Launch.Doc?) {
    Card(modifier = modifier.padding(10.dp)) {
        Column {
            Text(text = "Rocket")
            launchDoc?.rocket?.name?.let { LaunchDetailContent(title = "Model", subtitle = it) }
            LaunchDetailContent(title = "Launch window", subtitle = launchDoc?.window.toString())
            LaunchDetailContent(title = "Launch success", subtitle = launchDoc?.success.toString())
            if (launchDoc?.success == false) {
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LaunchDetailContent(title: String, subtitle: String) {
    ListItem(
        text = { Text(text = title) },
        trailing = { Text(text = subtitle) }
    )
}