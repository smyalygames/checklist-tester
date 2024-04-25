package tab.procedure

import InterfaceState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject

class Actions : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<InterfaceState>()
        val columnPadding = 24.dp

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
                    .verticalScroll(rememberScrollState())
                    .padding(top = columnPadding, bottom = columnPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    Modifier.fillMaxWidth(0.7F),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text(
                        text = "Edit Actions",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Button(
                        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                        onClick = {
                            // TODO make checks
                            navigator.pop()
                            viewModel.procedureId = null
                        }
                    ) {
                        Icon(Icons.Outlined.Add, "Create Procedure", modifier = Modifier.size(18.dp))
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            "Create",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}
