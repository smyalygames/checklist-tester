package io.anthonyberg.connector.shared.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActionResult(
    @SerialName("id")
    val id: Int,
    @SerialName("test_id")
    val testId: Int,
    @SerialName("action_id")
    val actionId: Int,
    @SerialName("init_state")
    val initState: String,
    @SerialName("end_state")
    val endState: String?,
    @SerialName("description")
    val description: String,
    @SerialName("start_utc")
    val startUTC: String,
    @SerialName("end_utc")
    val endUTC: String?,
) {
    // Convert String time to LocalDateTime variable
    var start = startUTC.toInstant().toLocalDateTime(TimeZone.UTC)
    var end = endUTC?.toInstant()?.toLocalDateTime(TimeZone.UTC)
}
