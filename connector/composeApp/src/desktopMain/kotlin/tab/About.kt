package tab
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.info_24px
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

class About : Tab {
    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "About"
            val icon = painterResource(Res.drawable.info_24px)

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
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "The Checklist Tester is developed by Anthony Berg.",
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(24.dp))
            Text("The aim of this Checklist Tester is to test checklists in a simulator " +
                    "without the need for a pilot and provide feedback on the results of the checklist.",
                textAlign = TextAlign.Center)
        }
    }
}
