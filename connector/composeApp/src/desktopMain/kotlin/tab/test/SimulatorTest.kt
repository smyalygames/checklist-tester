package tab.test
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import cafe.adriel.voyager.navigator.tab.*
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.check_box_24px
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

object SimulatorTest : Tab {
    private fun readResolve(): Any = SimulatorTest

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Simulator Test"
            val icon = painterResource(Res.drawable.check_box_24px)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon,
                )
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(RunTest) {
            val tabNavigator = LocalTabNavigator.current

            Scaffold(
                topBar = {
                    SecondaryTabRow(selectedTabIndex = tabNavigator.current.options.index.toInt()) {
                        TabItem(RunTest)
                        TabItem(TestResult)
                    }
                },
                content = { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        CurrentTab()
                    }
                }
            )
        }

    }

    @Composable
    private fun TabItem(
        tab: Tab
    ) {
        val tabNavigator = LocalTabNavigator.current

        Tab(
            text = { Text(text = tab.options.title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab }
        )
    }
}
