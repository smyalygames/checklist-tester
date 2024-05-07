package io.anthonyberg.connector.shared.vdmj

import io.anthonyberg.connector.shared.entity.Action
import io.anthonyberg.connector.shared.vdmj.type.*
import io.anthonyberg.connector.shared.xpc.XPC
import io.ktor.utils.io.errors.*

/**
 * VDMJ transactions used to control the aircraft in the simulator
 * and run steps
 */
class VDMJTransaction(val actions: List<Action>, xpc: XPC) {
    private val vdmj = VDMJ()

    private var aircraft: Aircraft

    init {
        // Check X-Plane is running
        if (!xpc.connected()) {
            throw IOException("Could not connect to X-Plane")
        }

        // Create Aircraft
        val items: MutableMap<String, ItemObject> = mutableMapOf()
        val procedure: MutableList<ProcedureItem> = mutableListOf()
        for (item: Action in actions) {
            val dref = item.type
            val type = ItemType.SWITCH
            val initDref = xpc.getState(dref)[0].toInt()
            val goal = item.goal.toInt()
            val middlePosition: Boolean = goal > 1

            items[dref] = ItemObject(
                type = type,
                item = Switch(
                    position = initDref,
                    middlePosition = middlePosition
                )
            )
            procedure.addLast(
                ProcedureItem(
                    dref = dref,
                    type = type,
                    goal = goal,
                    complete = false
                )
            )
        }

        aircraft = Aircraft(items = items, procedure = procedure)
    }

    fun nextStep() {

    }
}
