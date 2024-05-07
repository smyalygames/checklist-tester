package io.anthonyberg.connector.shared.vdmj.type

data class ItemObject(val type: ItemType, val item: Switch) { // TODO add types for other items
    /**
     * Converts to String for a VDM representation of an ItemObject record
     *
     * @return String representation for VDM ItemObject
     */
    fun toVDMString(): String {
        return "mk_ItemObject(${type.toVDMString()}, ${item.toVDMString()})"
    }

}
