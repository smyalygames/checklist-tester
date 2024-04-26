package tab.procedure

import InterfaceState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.save_24px
import io.anthonyberg.connector.shared.entity.Action
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class Actions (dbActions: List<Action>) : Screen {
    private val columnPadding = 24.dp
    private val itemPadding = 24.dp

    private var inputs = mutableStateListOf<Action>()

    init {
        inputs.addAll(dbActions)
    }

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<InterfaceState>()
        val state = rememberLazyListState(0)

        // Sends to screen model that Actions has been loaded
        val screenModel = getScreenModel<ActionsScreenModel>()
        screenModel.loadedActions()

        // Checks if a project has been selected before viewing contents
        if (viewModel.procedureId == null) {
            navigator.pop()
        }

        Scaffold(
            topBar = {
                IconButton(
                    onClick = {
                        navigator.pop()
                        viewModel.procedureId = null
                    }
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Back Arrow")
                }
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(top = columnPadding, bottom = columnPadding),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.7F),
                ) {
                    LazyColumn(
                        state = state,
                        verticalArrangement = Arrangement.spacedBy(itemPadding)
                    ) {

                        item {
                            header()
                        }

                        items(
                            items = inputs,
                            key = { input -> input.id }
                        ) { item ->
                            actionItem(item)
                        }

                        item {
                            footer(navigator, viewModel)
                        }
                    }

                    VerticalScrollbar(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(
                            scrollState = state,
                        ),
                    )
                }
            }
        }
    }

    @Composable
    private fun header() {
        Text(
            text = "Edit Actions",
            style = MaterialTheme.typography.headlineSmall
        )
    }

    @Composable
    private fun actionItem(item: Action) {
        Column (
            verticalArrangement = Arrangement.spacedBy(itemPadding)
        ) {
            Text(text = "Action ${item.id}")

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(itemPadding)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    value = item.type,
                    onValueChange = { inputs[item.id] = inputs[item.id].copy(type = it) },
                    label = { Text("Dataref Name") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = item.goal,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { inputs[item.id] = inputs[item.id].copy(goal = it) },
                    label = { Text("Desired State") },
                    singleLine = true
                )
            }

            HorizontalDivider()
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun footer(navigator: Navigator, viewModel: InterfaceState) {
        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Add Step
            FilledTonalButton(
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                onClick = {
                    // TODO make this a proper data array for each item in checklist
                    val procedureId = viewModel.procedureId
                    if (procedureId != null) {
                        inputs += createEmptyAction(procedureId)
                    }
                }
            ) {
                Icon(Icons.Outlined.Add, "Add", modifier = Modifier.size(18.dp))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "Add Step",
                    style = MaterialTheme.typography.labelLarge,
                )
            }

            // Save
            Button(
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                onClick = {
                    // TODO make checks
                    // TODO save to database
                    navigator.pop()
                    viewModel.procedureId = null
                }
            ) {
                Icon(painterResource(Res.drawable.save_24px), "Save", modifier = Modifier.size(18.dp))
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    "Save",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }

    private fun createEmptyAction(procedureId: Int): Action {
        val index = inputs.size

        val action = Action(
            id = index,
            procedureId = procedureId,
            step = index,
            type = "",
            goal = ""
        )

        return action
    }
}
