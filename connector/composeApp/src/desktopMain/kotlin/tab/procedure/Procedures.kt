package tab.procedure

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class Procedures : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Procedures"
            val icon = rememberVectorPainter(Icons.Outlined.Edit)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon,
                )
            }
        }


    @Composable
    override fun Content() {
        Navigator(ListProcedures()) {
            CurrentScreen()
        }
    }
}
