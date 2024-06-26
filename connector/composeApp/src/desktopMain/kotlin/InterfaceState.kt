import androidx.compose.runtime.mutableStateOf
import org.koin.core.component.KoinComponent

class InterfaceState : KoinComponent {
    var simConnection = mutableStateOf(false)
    var projectId: Int? = null
    var procedureId: Int? = null
    var testId: Int? = null
    var altitude: Int? = null
    var speed: Int? = null

    val projectSelected: Boolean
        get() = projectId != null

}
