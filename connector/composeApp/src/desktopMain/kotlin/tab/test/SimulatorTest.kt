package tab.test
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
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

    @Composable
    override fun Content() {
        Text("Simulator Test")
    }
}
