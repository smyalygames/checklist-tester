package tab.procedure

import InterfaceState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.expand_more_24px
import io.anthonyberg.connector.shared.entity.Procedure
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tab.LoadingScreen
import tab.test.SimulatorTest

class ListProcedures (
    private val procedures: List<Procedure>
) : Screen {
    private val optionInput = mutableStateListOf("", "", "")

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<InterfaceState>()
        val screenModel = getScreenModel<ActionsScreenModel>()
        val state by screenModel.state.collectAsState()

        when (val s = state) {
            is ActionsState.Idle -> { }
            is ActionsState.Loading -> navigator.push(LoadingScreen("Actions"))
            is ActionsState.Result -> {
                navigator.pop()
                navigator.push(Actions(s.actions))
            }
        }


        val lazyState = rememberLazyListState(0)

        Scaffold (
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { navigator.push(CreateProcedure()) },
                    content = {
                        Icon(Icons.Outlined.Add, "Create New Procedure")
                        Text("New Procedure")
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.7F),
                ) {
                    LazyColumn(state = lazyState) {
                        items(procedures) { procedure ->
                            ProcedureItem(procedure, viewModel, screenModel)
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
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun ProcedureItem(procedure: Procedure, viewModel: InterfaceState, screenModel: ActionsScreenModel) {
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopEnd)
        ) {
            ListItem(
                modifier = Modifier
                    .clickable(
                        enabled = true,
                        onClick = {
                            expanded = true
                        }
                    ),
                overlineContent = { Text(procedure.type) },
                headlineContent = { Text(procedure.name) },
                trailingContent = { Icon(painterResource(Res.drawable.expand_more_24px), "Open Procedure Menu") }
            )

            ProcedureMenu(
                procedure = procedure,
                viewModel = viewModel,
                screenModel = screenModel,
                expanded = expanded,
                onDismissRequest = { expanded = false }
            )
        }

        HorizontalDivider()
    }

    @Composable
    private fun ProcedureMenu(
        procedure: Procedure,
        viewModel: InterfaceState,
        screenModel: ActionsScreenModel,
        expanded: Boolean,
        onDismissRequest: () -> Unit
    ) {
        val tabNavigator = LocalTabNavigator.current
        var openRunDialog by remember { mutableStateOf(false) }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    viewModel.procedureId = procedure.id
                    screenModel.getActions()
                },
                leadingIcon = {
                    Icon(Icons.Outlined.Edit, "Edit Procedure")
                },
            )

            DropdownMenuItem(
                text = { Text("Run Test") },
                onClick = {
                    viewModel.procedureId = procedure.id
                    openRunDialog = true
                },
                leadingIcon = {
                    Icon(Icons.Outlined.PlayArrow, "Run Procedure Tests")
                },
            )
        }

        when {
            openRunDialog -> {
                BeforeStartDialog(
                    onDismissRequest = { openRunDialog = false },
                    onConfirmation = {
                        openRunDialog = false
                        viewModel.altitude = optionInput[0].toIntOrNull()
                        viewModel.speed = optionInput[1].toIntOrNull()
                        tabNavigator.current = SimulatorTest
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun BeforeStartDialog(
        onDismissRequest: () -> Unit,
        onConfirmation: () -> Unit,
    ) {
        var modeSelect by remember { mutableStateOf(0) }
        val modeOptions = listOf("None", "Pre-Configure")

        Dialog(onDismissRequest = { onDismissRequest() }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Pre-Run Commands",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )

                    MultiChoiceSegmentedButtonRow (
                        modifier = Modifier.padding(8.dp)
                    ) {
                        modeOptions.forEachIndexed { index, label ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = modeOptions.size
                                ),
                                onCheckedChange = { modeSelect = index },
                                checked = (index == modeSelect),
                            ) {
                                Text(label)
                            }
                        }
                    }

                    when {
                        modeSelect == 1 -> {
                            BeforeStartForm()
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = { onConfirmation() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Run")
                        }

                        TextButton(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }

    }

    @Composable
    private fun BeforeStartForm() {
        val checkedListState = remember { mutableStateListOf<Int>() }
        val options = listOf("Set Altitude", "Set Speed", "Add Actions")

        Column(
            modifier = Modifier
                .fillMaxWidth(0.9F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            options.forEachIndexed { index, label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(8.dp)
                        .toggleable(
                            value = index in checkedListState,
                            onValueChange = {
                                if (index in checkedListState) {
                                    checkedListState.remove(index)
                                } else {
                                    checkedListState.add(index)
                                }
                            },
                            role = Role.Checkbox,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = index in checkedListState,
                        onCheckedChange = null,
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                if (index in checkedListState) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = optionInput[index],
                        onValueChange = { optionInput[index] = it },
                        label = { Text(options[index]) },
                        singleLine = true
                    )
                }
            }

        }
    }
}
