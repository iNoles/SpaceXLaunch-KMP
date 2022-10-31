package com.jonathansteele.spacexlaunch.android

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jonathansteele.spacexlaunch.Launch
import com.jonathansteele.spacexlaunch.SpaceXAPI
import com.jonathansteele.spacexlaunch.android.ui.theme.SpaceXLaunchTheme
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val MediumDateFormatter by lazy {
    DateTimeFormatter
        .ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
        .withZone(ZoneId.systemDefault())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchScreen(
    onNavigateToDetail: (Int) -> Unit
) {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(text = "SpaceX Launches") }) }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)) {
            LaunchContent(onNavigateToDetail = onNavigateToDetail)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LaunchContent(modifier: Modifier = Modifier, onNavigateToDetail: (Int) -> Unit) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        // Override the indicator, using the provided pagerTabIndicatorOffset modifier
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        Tab(
            text = { Text("Latest") },
            selected = pagerState.currentPage == 0,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            },
        )
        Tab(
            text = { Text("Upcoming") },
            selected = pagerState.currentPage == 1,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(1)
                }
            },
        )
    }

    val launchState = SpaceXAPI.fetchAllLaunches().collectAsState(initial = emptyList())

    HorizontalPager(
        modifier = modifier,
        count = 2,
        state = pagerState,
    ) { page ->
        val filter = when(page) {
            0 -> launchState.value?.filter {
                !it.upcoming
            }
            1 -> launchState.value?.filter {
                it.upcoming
            }
            else -> launchState.value
        }
        LaunchListBody(launchDocs = filter ?: emptyList(), onNavigateToDetail = onNavigateToDetail)
    }
}

@Composable
fun LaunchListBody(
    modifier: Modifier = Modifier,
    launchDocs: List<Launch.Doc>,
    onNavigateToDetail: (Int) -> Unit
) {
    LazyColumn {
        items(
            items = launchDocs.sortedByDescending { it.flightNumber },
            key = {
                it.id
            }
        ) {
            LaunchItem(launchDocs = it, onNavigateToDetail = onNavigateToDetail)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchItem(
    launchDocs: Launch.Doc,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    onNavigateToDetail: (Int) -> Unit
) {
    Card(modifier = modifier.padding(10.dp)) {
        ListItem(
            modifier = modifier.clickable {
                onNavigateToDetail(launchDocs.flightNumber)
            },
            leadingContent = {
                LaunchPatchImage(urlString = launchDocs.links.patch.small, iconModifier.size(64.dp))
            },
            headlineText = { Text(text = launchDocs.name) },
            supportingText = { LaunchDate(instant = launchDocs.dateUtc.toJavaInstant()) },
            trailingContent = { Text("# ${launchDocs.flightNumber}") }
        )
    }
}

@Composable
fun LaunchDate(instant: java.time.Instant) {
    Text(text = MediumDateFormatter.format(instant))
}

@Composable
fun LaunchPatchImage(urlString: String?, modifier: Modifier = Modifier) {
    AsyncImage(
        model = urlString,
        contentDescription = null,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LaunchScreenPreview() {
    SpaceXLaunchTheme {
        LaunchScreen(onNavigateToDetail = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchListPreview() {
    val launchDocs = listOf(
        Launch.Doc(
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
    )
    SpaceXLaunchTheme {
        LaunchListBody(launchDocs = launchDocs, onNavigateToDetail = {})
    }
}
