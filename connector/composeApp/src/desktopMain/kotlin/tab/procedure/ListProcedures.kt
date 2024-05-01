package tab.procedure

import InterfaceState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                            procedureItem(procedure, viewModel, screenModel)
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
    private fun procedureItem(procedure: Procedure, viewModel: InterfaceState, screenModel: ActionsScreenModel) {
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

            procedureMenu(
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
    private fun procedureMenu(
        procedure: Procedure,
        viewModel: InterfaceState,
        screenModel: ActionsScreenModel,
        expanded: Boolean,
        onDismissRequest: () -> Unit
    ) {
        val tabNavigator = LocalTabNavigator.current

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
                    tabNavigator.current = SimulatorTest
                },
                leadingIcon = {
                    Icon(Icons.Outlined.PlayArrow, "Run Procedure Tests")
                },
            )
        }
    }
}
