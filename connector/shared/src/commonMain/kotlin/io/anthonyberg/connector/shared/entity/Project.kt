package io.anthonyberg.connector.shared.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("aircraft_type")
    val aircraftType: String,
    @SerialName("created_utc")
    val createdUTC: String,
    @SerialName("modified_utc")
    val modifiedUTC: String?,
) {
    // Convert String time to LocalDateTime variable
    var created = createdUTC.toInstant().toLocalDateTime(TimeZone.UTC)
    var modified = modifiedUTC?.toInstant()?.toLocalDateTime(TimeZone.UTC)
}
