import org.koin.core.component.KoinComponent

class InterfaceState : KoinComponent {
    var simConnection: Boolean = false
    var projectId: Int? = null
    var procedureId: Int? = null

    val projectSelected: Boolean
        get() = projectId != null

}
