import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchItem(
    launchDocs: Launch.Doc,
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    launchSelected: (person: Launch.Doc) -> Unit
) {
    Card(modifier = modifier.padding(16.dp)) {
        ListItem(
            modifier = modifier.clickable {
                launchSelected(launchDocs)
            },
            leadingContent = {
                LoadIcon(launchDocs, iconModifier)
            },
            headlineText = { Text(text = launchDocs.name) },
            supportingText = { LaunchDate(instant = launchDocs.dateUtc) },
            trailingContent = { Text("# ${launchDocs.flightNumber}") }
        )
    }
}

