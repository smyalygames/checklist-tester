
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.folder_24px
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

object Projects : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Projects"
            val icon = painterResource(Res.drawable.folder_24px)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @Composable
    override fun Content() {
        noProjects()
    }

    @Composable
    private fun noProjects() {
        Column (
            modifier = Modifier.fillMaxHeight().fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Welcome!", style = MaterialTheme.typography.displayMedium)
                Text("You currently have no projects...", style = MaterialTheme.typography.displaySmall)
            }

            Button(
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                onClick = { /* TODO */ }
            ) {
                Icon(Icons.Outlined.Add, "Create New Project", modifier = Modifier.size(18.dp))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Create Project", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
