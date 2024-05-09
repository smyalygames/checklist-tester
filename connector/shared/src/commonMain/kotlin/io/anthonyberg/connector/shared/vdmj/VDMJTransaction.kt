package io.anthonyberg.connector.shared.vdmj

import io.anthonyberg.connector.shared.entity.Action
import io.anthonyberg.connector.shared.vdmj.type.*
import io.anthonyberg.connector.shared.xpc.XPC
import io.ktor.utils.io.errors.*

/**
 * VDMJ transactions used to control the aircraft in the simulator
 * and run steps
 */
class VDMJTransaction(val actions: List<Action>, private val xpc: XPC) {
    private val vdmj = VDMJ()

    private val drefs: Array<String> = actions.map { it.type }.toTypedArray()

    private var aircraft: Aircraft
    private var step = 1

    init {
        // Check X-Plane is running
        if (!xpc.connected()) {
            throw IOException("Could not connect to X-Plane")
        }

        // Create Aircraft
        val items = getAircraftState()

        val procedures: MutableList<ProcedureItem> = actions.map {
            ProcedureItem(
                dref = it.type,
                type = ItemType.SWITCH,
                goal = it.goal.toInt(),
                complete = false
            )
        }.toMutableList()

        aircraft = Aircraft(items = items, procedure = procedures)
    }

    suspend fun expectedEndState(): String {
        val command = "p complete_procedure(${aircraft.toVDMString()})"

        println(command)

        val result = vdmj.run(command = command)

        return result.output
    }

//    fun nextStep() {
//        val command = "do_proc_item("
//    }

    /**
     * Gets the state of all the DREFs in X-Plane for [Aircraft.items]
     *
     * @return Map with key that is a DREF and value [ItemObject]
     */
    private fun getAircraftState(): Map<String, ItemObject> {
        val initDrefs = xpc.getStates(drefs = drefs)

        val items: Map<String, ItemObject> = actions.associateBy(
            { it.type },
            { ItemObject(
                type = ItemType.SWITCH,
                item = Switch(
                    position = initDrefs[it.step][0].toInt(),
                    middlePosition = it.goal.toInt() > 1
                )
            )}
        )

        return items
    }
}
