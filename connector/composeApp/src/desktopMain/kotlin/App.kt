
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import connector.composeapp.generated.resources.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import theme.AppTheme

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    AppTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            AppScaffold()
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}

@Composable
fun AppScaffold() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(drawerState, scope)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            NavigationDrawer(drawerState)
            Text("Test")
        }
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
        }
        // TODO add Simulator Status
    )
}

/**
 * Navigation drawer from the left side
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavigationDrawer(drawerState: DrawerState) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.verticalScroll(rememberScrollState()) ) {
                    Text("Project Name...", modifier = Modifier.padding(16.dp))
                    NavigationDrawerItem(
                        label = { Text(text = "Projects") },
                        icon = { Icon(painterResource(Res.drawable.folder_24px), null) },
                        selected = false,
                        onClick = { /* TODO */ },
                    )
                    HorizontalDivider()

                    Text("Tester", modifier = Modifier.padding(16.dp))
                    NavigationDrawerItem(
                        label = { Text(text = "Procedures") },
                        icon = { Icon(imageVector = Icons.Outlined.Edit, null) },
                        selected = false,
                        onClick = { /* TODO */ },
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Simulator Test") },
                        icon = { Icon(painterResource(Res.drawable.check_box_24px), null) },
                        selected = false,
                        onClick = { /* TODO */ },
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "Test Results") },
                        icon = { Icon(painterResource(Res.drawable.history_24px), null) },
                        selected = false,
                        onClick = { /* TODO */ },
                    )
                    HorizontalDivider()

                    Text("Application", modifier = Modifier.padding(16.dp))
                    NavigationDrawerItem(
                        label = { Text(text = "Settings") },
                        icon = { Icon(imageVector = Icons.Outlined.Settings, null) },
                        selected = false,
                        onClick = { /* TODO */ },
                    )
                    NavigationDrawerItem(
                        label = { Text(text = "About") },
                        icon = { Icon(painterResource(Res.drawable.info_24px), null) },
                        selected = false,
                        onClick = { /* TODO */ },
                    )
                }
            }
        }
    ) {}
}
