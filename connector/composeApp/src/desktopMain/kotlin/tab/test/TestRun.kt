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
import io.github.oshai.kotlinlogging.KotlinLogging

class TestRun (
    private val actions: List<Action>
) : Screen {

    private val logger = KotlinLogging.logger {}
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
            screenModel.init(actions)
        }

        when (val s = state) {
            is TestState.Init -> logger.info { "Loading Simulator Tests" }
            is TestState.NoSimulator -> {
                logger.warn { "Could not connect to the simulator" }
                Text("Could not connect to the simulator!")
                return
            }
            is TestState.Ready -> {
                logger.info { "Loaded Simulator Tests" }

                screenModel.runAction(actions[step])
            }
            is TestState.Running -> logger.info { "Running Action: ${s.step}" }
            is TestState.Complete -> {
                logger.info { "Completed test for action: ${s.step}" }
                tested.add(s.pass)

                step += 1
                if (step == actions.size) {
                    logger.info { "Completed all tests" }
                    screenModel.end()
                } else {
                    screenModel.runAction(actions[step])
                }
            }
            is TestState.Idle -> running = false
            is TestState.Error -> {
                logger.error { "An error occurred!" }
                return Text("An error occurred!")
            }
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
