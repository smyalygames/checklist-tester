
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import element.SimulatorStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import tab.About
import tab.Settings
import tab.test.SimulatorTest
import tab.TestResults
import tab.procedure.Procedures
import tab.project.Projects
import theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        AppScaffold()
    }
}

@Composable
fun AppScaffold() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerScope = rememberCoroutineScope()

    TabNavigator(Projects) {
        Scaffold(
            topBar = {
                TopBar(drawerState, drawerScope)
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    NavigationDrawer(drawerState = drawerState, drawerScope = drawerScope) {
                        KoinContext {
                            CurrentTab()
                        }
                    }
                }
            }
        )
    }
}



/**
 * Component for the bar on top of the screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    drawerState: DrawerState,
    drawerScope: CoroutineScope,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Text(
                "Checklist Tester",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                drawerScope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }

            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Open Navigation Bar",
                )
            }
        },
        actions = {
            SimulatorStatus().StatusCard()
        }
    )
}

/**
 * Navigation drawer from the left side
 */
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    drawerScope: CoroutineScope,
    content: @Composable() () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.verticalScroll(rememberScrollState()) ) {
                    Text("Project Name...", modifier = Modifier.padding(16.dp))
                    TabNavigationItem(tab = Projects, drawerState = drawerState, drawerScope = drawerScope)
                    HorizontalDivider()

                    Text("Tester", modifier = Modifier.padding(16.dp))
                    TabNavigationItem(tab = Procedures, drawerState = drawerState, drawerScope = drawerScope)
                    TabNavigationItem(tab = SimulatorTest, drawerState = drawerState, drawerScope = drawerScope)
                    TabNavigationItem(tab = TestResults(), drawerState = drawerState, drawerScope = drawerScope)
                    HorizontalDivider()

                    Text("Application", modifier = Modifier.padding(16.dp))
                    TabNavigationItem(tab = Settings(), drawerState = drawerState, drawerScope = drawerScope)
                    TabNavigationItem(tab = About(), drawerState = drawerState, drawerScope = drawerScope)
                }
            }
        }
    ) {
        content()
    }
}

/**
 * Creates button in NavigationDrawer
 */
@Composable
private fun TabNavigationItem(
    tab: Tab,
    drawerState: DrawerState,
    drawerScope: CoroutineScope
) {
    val tabNavigator = LocalTabNavigator.current

    NavigationDrawerItem(
        label = { Text(tab.options.title) },
        icon = { tab.options.icon?.let { Icon(it, null) } },
        selected = tabNavigator.current == tab,
        onClick = {
            drawerScope.launch {
                tabNavigator.current = tab
                drawerState.apply { close() }
            }
        },
    )
}
