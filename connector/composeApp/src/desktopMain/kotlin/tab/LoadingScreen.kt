package tab

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

/**
 * Loading screen for use when using a ScreenModel in a loading state.
 * Includes a CircularProgressIndicator with text below: "Loading *loadingTitle*"
 *
 * @param loadingTitle `String` of the page that is being loaded.
 */
class LoadingScreen (private val loadingTitle: String) : Screen {

    @Composable
    override fun Content() {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.onSurface,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(Modifier.size(64.dp))

            Text("Loading $loadingTitle", style = MaterialTheme.typography.displaySmall )
        }
    }

}
