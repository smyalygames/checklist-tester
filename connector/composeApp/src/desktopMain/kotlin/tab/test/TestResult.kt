package tab.test

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object TestResult : Tab {
    private fun readResolve(): Any = TestResult

    override val options: TabOptions
        @Composable
        get() {
            val title = "Test Result"

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                )
            }
        }

    @Composable
    override fun Content() {
        Text("Test Results")
    }
}
