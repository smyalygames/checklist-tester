
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.rule_24px
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Checklist Tester", icon = painterResource(Res.drawable.rule_24px)) {
        App()
    }
}
