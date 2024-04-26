package io.anthonyberg.connector.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Action(
    @SerialName("id")
    val id: Int,
    @SerialName("procedure_id")
    val procedureId: Int,
    @SerialName("step")
    val step: Int,
    @SerialName("type")
    val type: String,
    @SerialName("goal")
    val goal: String,
)
