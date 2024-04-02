package tab.procedure

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class ListProcedures : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val state = rememberLazyListState(0)

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
                    LazyColumn(state = state) {
                        items(50) { index ->
                            ListItem(
                                modifier = Modifier
                                    .clickable(
                                        enabled = true,
                                        onClick = {
                                            // TODO add procedure editor
                                        }
                                    ),
                                overlineContent = { Text("Emergency") },
                                headlineContent = { Text("Procedure $index") },
                                trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Open Procedure") }
                            )
                            HorizontalDivider()
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
}
