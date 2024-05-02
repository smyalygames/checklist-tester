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
import cafe.adriel.voyager.koin.getScreenModel
import io.anthonyberg.connector.shared.entity.Action

class TestRun (
    private val actions: List<Action>
) : Screen {

    private var tested = mutableStateListOf<Boolean>()

    @Composable
    override fun Content() {
        val lazyState = rememberLazyListState(0)
        var running by remember { mutableStateOf(false) }
        var step by remember { mutableIntStateOf(0) }

        val screenModel = getScreenModel<TestScreenModel>()
        val state by screenModel.state.collectAsState()

        if (!running and (tested.size == 0)) {
            running = true
            screenModel.init()
        }

        when (val s = state) {
            is TestState.Init -> println("Loading Simulator Tests")
            is TestState.NoSimulator -> {
                running = false
                Text("Could not connect to the simulator!")
                return
            }
            is TestState.Ready -> {
                println("Loaded Simulator Tests")

                screenModel.runAction(actions[step])
            }
            is TestState.Running -> println("Running Action: ${s.step}")
            is TestState.Complete -> {
                tested.add(s.pass)

                step += 1
                if (step == actions.size) {
                    screenModel.end()
                } else {
                    screenModel.runAction(actions[step])
                }
            }
            is TestState.Idle -> running = false
            is TestState.Error -> return Text("An error occurred!")
        }

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
}
