package tab.test

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object RunTest : Tab {
    private fun readResolve(): Any = RunTest

    override val options: TabOptions
    @Composable
    get() {
        val title = "Run Test"

        return remember {
            TabOptions(
                index = 0u,
                title = title,
            )
        }
    }

    @Composable
    override fun Content() {
        Text("Simulator Running Tests...")
    }
}
