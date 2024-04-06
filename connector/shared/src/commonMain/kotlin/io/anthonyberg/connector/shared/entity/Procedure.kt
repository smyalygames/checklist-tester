package io.anthonyberg.connector.shared.entity

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Procedure(
    @SerialName("id")
    val id: Int,
    @SerialName("project_id")
    val projectId: Int,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: String,
    @SerialName("description")
    val description: String,
    @SerialName("created_utc")
    val createdUTC: String,
    @SerialName("modified_utc")
    val modifiedUTC: String?,
) {
    // Convert String time to LocalDateTime variable
    var created = createdUTC.toInstant().toLocalDateTime(TimeZone.UTC)
    var modified = modifiedUTC?.toInstant()?.toLocalDateTime(TimeZone.UTC)
}
