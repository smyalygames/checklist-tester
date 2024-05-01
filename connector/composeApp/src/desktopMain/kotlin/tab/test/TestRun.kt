package tab.test

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import io.anthonyberg.connector.shared.entity.Action

class TestRun (
    private val actions: List<Action>
) : Screen {
    @Composable
    override fun Content() {
        val lazyState = rememberLazyListState(0)

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.7F)
            ) {
                LazyColumn {
                    items(actions) { action ->
                        ActionItem(action)
                    }
                }

                VerticalScrollbar(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(
                        scrollState = lazyState,
                    ),
                )
            }
        }
    }

    @Composable
    private fun ActionItem(action: Action) {
        ListItem(
            headlineContent = { Text(action.type) },
            supportingContent = { Text("Goal: ${action.goal}") },
            leadingContent = { Icon(Icons.Outlined.Info, null) }
        )

        HorizontalDivider()
    }
}
