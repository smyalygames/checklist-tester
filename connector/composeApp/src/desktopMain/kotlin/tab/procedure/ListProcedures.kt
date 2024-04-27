package tab.procedure

import InterfaceState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.anthonyberg.connector.shared.entity.Procedure
import org.koin.compose.koinInject
import tab.LoadingScreen

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

    @Composable
    private fun procedureItem(procedure: Procedure, viewModel: InterfaceState, screenModel: ActionsScreenModel) {
        ListItem(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = {
                        viewModel.procedureId = procedure.id
                        screenModel.getActions()
                    }
                ),
            overlineContent = { Text(procedure.type) },
            headlineContent = { Text(procedure.name) },
            trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Open Procedure") }
        )
        HorizontalDivider()
    }
}
