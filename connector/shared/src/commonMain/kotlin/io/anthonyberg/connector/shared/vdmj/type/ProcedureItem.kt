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
        return "mk_ChecklistItem(\"$dref\", ${type.toVDMString()}, $goal, $complete)"
    }
}
