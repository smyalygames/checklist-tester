package tab.test

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.anthonyberg.connector.shared.entity.Action
import io.anthonyberg.connector.shared.xpc.XPC
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestRun (
    private val actions: List<Action>
) : Screen {

    private var tested = mutableStateListOf<Boolean>()

    @Composable
    override fun Content() {
        val lazyState = rememberLazyListState(0)
        var running by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Progress Indicator
            if (running) {
                LinearProgressIndicator(
                    progress = { tested.size / actions.size.toFloat() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

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

        if (!running and (tested.size == 0)) {
            scope.launch {
                running = true
                runSteps()
                running = false
            }
        }
    }

    @Composable
    private fun ActionItem(action: Action) {
        ListItem(
            headlineContent = { Text(action.type) },
            supportingContent = { Text("Goal: ${action.goal}") },
            leadingContent = {
                if (action.step > tested.size) {
                    Icon(Icons.Outlined.Info, "Waiting")
                } else if (action.step == tested.size) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    when (tested[action.step]) {
                        true -> Icon(Icons.Outlined.Check, "Passed")
                        false -> Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = "Failed",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        )

        HorizontalDivider()
    }

    private suspend fun runSteps() {
        val xpc = XPC()

        // Checks if the simulator is running before running the other tests
        if (!xpc.connected()) {
            for (action in actions) {
                tested.add(false)
            }
            return
        }

        for (action in actions) {
            delay(1000L)

            // TODO add try catch
            val result = xpc.runChecklist(action.type, action.goal.toInt())

            // TODO add more detailed results
            tested.add(result)
        }
    }
}
