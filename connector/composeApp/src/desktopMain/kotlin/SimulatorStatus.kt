
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SimulatorStatus {
    @Composable
    fun StatusCard() {
        val padding = 0.dp
        val spacer = 16.dp

        var connected by remember { mutableStateOf(false) }
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
                    refreshIndicator()
                } else {
                    Badge(
                        containerColor = if (connected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.error
                    )
                }

                Spacer(Modifier.size(spacer))
                Text("Simulator Status")
                IconButton(
                    onClick = {
                        refresh = true
                        scope.launch {
                            loadSimulator()
                            connected = !connected
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
    private fun refreshIndicator() {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }

    /**
     *  Purely for testing
     */
    private suspend fun loadSimulator() {
        delay(1000)
    }
}
