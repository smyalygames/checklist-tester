package io.anthonyberg.connector.shared.vdmj.type

data class ProcedureItem(
    val dref: String,
    val type: ItemType,
    val goal: Int, // TODO add checks for goal
    val complete: Boolean,
) {
    /**
     * Converts to a String for a VDM representation of a ChecklistItem record
     *
     * @return String record for VDM ChecklistItem
     */
    fun toVDMString(): String {
        return "mk_ChecklistItem(\"$dref\", ${type.toVDMString()}, ${getSwitchState()}, $complete)"
    }

    /**
     * Gets the VDM object for the switch state
     *
     * @return String representation for switch state
     */
    private fun getSwitchState(): String {
        return when (goal) {
            0 -> "<OFF>"
            1 -> "<ON>" // TODO add middle button
            2 -> "<ON>"
            else -> throw IllegalArgumentException("Position must be between 0 and 2")
        }
    }
}
