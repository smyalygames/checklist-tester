
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import connector.composeapp.generated.resources.Res
import connector.composeapp.generated.resources.rule_24px
import di.KoinInit
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.Koin

lateinit var koin: Koin

@OptIn(ExperimentalResourceApi::class)
fun main() {
    koin = KoinInit().init()
    koin.loadModules(
        listOf(),
    )

    return application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Checklist Tester",
            icon = painterResource(Res.drawable.rule_24px)
        ) {
            App()
        }
    }
}
