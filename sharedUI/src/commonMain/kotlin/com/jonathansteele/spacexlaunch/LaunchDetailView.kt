package com.jonathansteele.spacexlaunch

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

        items(launchDocs?.payloads ?: emptyList()) {
            Text(text = "Payloads")
            LaunchDetailContent(title = "Reused", subtitle = it.reused.toString())
            LaunchDetailContent(
                title = "Manufacturer",
                subtitle = it.manufacturers.joinToString(",")
            )
            LaunchDetailContent(title = "Customer", subtitle = it.customers.joinToString(","))
            LaunchDetailContent(
                title = "Nationality",
                subtitle = it.nationalities.joinToString(",")
            )
            LaunchDetailContent(title = "Orbit", subtitle = it.orbit.toString())

            val periphrasis = it.periapsisKm?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Periapsis", subtitle = periphrasis)

            val apoptosis = it.apoapsisKm?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Apoapsis", subtitle = apoptosis)

            val inclination = it.inclinationDeg?.toString() ?: "Unknown"
            LaunchDetailContent(title = "Inclination", subtitle = inclination)

            val period = it.periodMin?.toString() ?: "Unknown"
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