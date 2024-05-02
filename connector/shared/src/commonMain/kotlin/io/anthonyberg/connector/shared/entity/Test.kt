package io.anthonyberg.connector.shared.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Test(
    @SerialName("id")
    val id: Int,
    @SerialName("procedure_id")
    val procedureId: Int,
    @SerialName("actions")
    val actions: List<ActionResult>?,
    @SerialName("start_utc")
    val startUTC: String,
    @SerialName("end_utc")
    val endUTC: String?,
) {
    // Convert String time to LocalDateTime variable
    var start = startUTC.toInstant().toLocalDateTime(TimeZone.UTC)
    var end = endUTC?.toInstant()?.toLocalDateTime(TimeZone.UTC)
}
