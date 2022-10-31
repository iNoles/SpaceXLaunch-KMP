package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jonathansteele.spacexlaunch.Launch
import com.jonathansteele.spacexlaunch.SpaceXAPI
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceXLaunchTheme
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant

@Composable
fun LaunchDetailScreen(id: Int?, onBackClick: () -> Unit) {
    val launch = SpaceXAPI.fetchAllLaunches().collectAsState(initial = emptyList()).value!!
    val filter = launch.filter {
        it.flightNumber == id
    }
    if (filter.size == 1) {
        LaunchDetailBody(launchDoc = filter.last(), onBackClick = onBackClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailBody(launchDoc: Launch.Doc, onBackClick: () -> Unit) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Pop Back Navigation Stack"
                    )
                }
            },
            title = { Text(text = launchDoc.name) })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                LaunchHeader(launchDoc = launchDoc)
            }

            items(
                items = launchDoc.payloads,
                key = { payloads ->
                    payloads.id
                }
            ) {
                Text(text = "Payloads")
                LaunchDetailContent(title = "Reused", subtitle = it.reused.toString())
                LaunchDetailContent(title = "Manufacturer", subtitle = it.manufacturers.joinToString(","))
                LaunchDetailContent(title = "Customer", subtitle = it.customers.joinToString(","))
                LaunchDetailContent(title = "Nationality", subtitle = it.nationalities.joinToString(","))
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchHeader(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    launchDoc: Launch.Doc
) {
    Card(modifier = modifier.padding(10.dp)) {
        ListItem(
            leadingContent = {
                LaunchPatchImage(urlString = launchDoc.links.patch.small, iconModifier.size(64.dp))
            },
            headlineText = { Text(launchDoc.name) },
            supportingText = {
                LaunchDate(instant = launchDoc.dateUtc.toJavaInstant())
                Text(text = launchDoc.launchpad.name)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchDetailContent(modifier: Modifier = Modifier, title: String, subtitle: String) {
    ListItem(
        modifier = modifier,
        headlineText = { Text(text = title) },
        trailingContent = { Text(text = subtitle) }
    )
}

@Preview(showBackground = true)
@Composable
fun LaunchDetailScreenPreview() {
    val launchDocs = Launch.Doc(
        fairings = Launch.Doc.Fairings(
            recovered = false,
            recoveryAttempt = false,
            reused = false
        ),
        links = Launch.Doc.Links(
            patch = Launch.Doc.Links.Patch(
                small = "https://images2.imgbox.com/94/f2/NN6Ph45r_o.png",
            ),
            presskit = null,
            webcast = "https://www.youtube.com/watch?v=0a_00nJ_Y88"
        ),
        staticFireDateUtc = Instant.parse("2006-03-17T00:00:00.000Z"),
        window = 0,
        rocket = Launch.Doc.Rocket(
            name = "Falcon 1",
            id = "5e9d0d95eda69955f709d1eb",
        ),
        success = false,
        failures = listOf(
            Launch.Doc.Failure(
                time = 33,
                altitude = null,
                reason = "merlin engine failure"
            )
        ),
        details = "Engine failure at 33 seconds and loss of vehicle",
        payloads = listOf(
            Launch.Doc.Payload(
                dragon = Launch.Doc.Payload.Dragon(
                    capsule = null
                ),
                reused = false,
                customers = listOf("DARPA"),
                nationalities = listOf("United States"),
                manufacturers = listOf("SSTL"),
                massKg = 20.0,
                orbit = "LEO",
                periapsisKm = 400.0,
                apoapsisKm = 500.0,
                inclinationDeg = 39.0,
                periodMin = null,
                id = "5eb0e4b5b6c3bb0006eeb1e1"
            )
        ),
        launchpad = Launch.Doc.Launchpad(
            name = "Kwajalein Atoll",
            fullName = "Kwajalein Atoll Omelek Island",
            locality = "Omelek Island",
            region = "Marshall Islands",
            latitude = 9.0477206,
            longitude = 167.7431292,
            launchAttempts = 5,
            launchSuccesses = 2,
            status = "retired",
            details = "SpaceX's original pad, where all of the Falcon 1 flights occurred (from 2006 to 2009). It would have also been the launch site of the Falcon 1e and the Falcon 9, but it was abandoned as SpaceX ended the Falcon 1 program and decided against upgrading it to support Falcon 9, likely due to its remote location and ensuing logistics complexities.",
            id = "5e9e4502f5090995de566f86"
        ),
        flightNumber = 1,
        name = "FalconSat",
        dateUtc = Instant.parse("2006-03-24T22:30:00.000Z"),
        datePrecision = "hour",
        upcoming = false,
        cores = listOf(
            Launch.Doc.Core(
                core = Launch.Doc.Core.Core(
                    block = null,
                    reuseCount = 0,
                    rtlsAttempts = 0,
                    rtlsLandings = 0,
                    asdsAttempts = 0,
                    asdsLandings = 0,
                    lastUpdate = "Engine failure at T+33 seconds resulted in loss of vehicle",
                    serial = "Merlin1A",
                    status = "lost",
                    id = "5e9e289df35918033d3b2623"
                ),
                flight = 1,
                gridfins = false,
                legs = false,
                reused = false,
                landingAttempt = false,
                landingSuccess = null,
                landingType = null,
                landpad = null
            )
        ),
        launchLibraryId = null,
        id = "5eb87cd9ffd86e000604b32a"
    )
    SpaceXLaunchTheme {
        LaunchDetailBody(launchDoc = launchDocs, onBackClick = {})
    }
}
