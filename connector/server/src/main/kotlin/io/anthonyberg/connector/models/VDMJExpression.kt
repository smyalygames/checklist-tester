package io.anthonyberg.connector.models

import com.fujitsu.vdmj.ExitStatus
import kotlinx.serialization.Serializable

@Serializable
data class VDMJExpression(val output: String, val exitStatus: ExitStatus)
