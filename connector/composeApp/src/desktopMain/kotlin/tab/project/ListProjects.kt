package tab.project

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import io.anthonyberg.connector.shared.entity.Project
import org.koin.compose.koinInject
import tab.procedure.Procedures

class ListProjects(private val projects: List<Project>) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current
        val state = rememberLazyListState(0)

        val viewModel = koinInject<InterfaceState>()

        Scaffold (
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { navigator.push(CreateProject()) },
                    content = {
                        Icon(Icons.Outlined.Add, "Create New Project")
                        Text("New Project")
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
                        items(projects) { project ->
                            ProjectItem(project, viewModel, tabNavigator)
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
    private fun ProjectItem(project: Project, viewModel: InterfaceState, tabNavigator: TabNavigator) {
        ListItem(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = {
                        viewModel.projectId = project.id

                        // Go to procedures page once project has been selected
                        tabNavigator.current = Procedures
                    }
                ),
            overlineContent = { Text(project.aircraftType) },
            headlineContent = { Text(project.name) },
            trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Open Project") }
        )
        HorizontalDivider()
    }
}
