package element
import InterfaceState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.link_24px
import connector.composeapp.generated.resources.link_off_24px
import io.anthonyberg.connector.shared.xpc.XPC
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

class SimulatorStatus {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun StatusCard() {
        val padding = 0.dp
        val spacer = 8.dp

        val viewModel = koinInject<InterfaceState>()
        var refresh by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
        ) {
            Row(
                Modifier
                    .padding(padding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.size(spacer - padding))
                if (refresh) {
                    RefreshIndicator()
                } else if (viewModel.simConnection.value) {
                    Icon(
                        painter = painterResource(Res.drawable.link_24px),
                        contentDescription = "Simulator connected"
                    )
                } else {
                    Icon(
                        painter = painterResource(Res.drawable.link_off_24px),
                        contentDescription = "Simulator not connected",
                        tint = MaterialTheme.colorScheme.error,
                    )
                }

                Spacer(Modifier.size(spacer))
                Text("Simulator Status")
                IconButton(
                    onClick = {
                        refresh = true
                        scope.launch {
                            loadSimulator(viewModel)
                            refresh = false
                        }
                    },
                    enabled = !refresh,
                ) {
                    Icon(Icons.Outlined.Refresh, "Re-check Simulator Status")
                }
            }
        }
    }

    @Composable
    private fun RefreshIndicator() {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

    /**
     *  Purely for testing
     */
    private fun loadSimulator(viewModel: InterfaceState) {
        val xpc = XPC()
        viewModel.simConnection.value = xpc.connected()
    }
}
