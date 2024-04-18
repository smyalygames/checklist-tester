package io.anthonyberg.connector.routes

import io.anthonyberg.connector.shared.vdmj.vdmjExecute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.vdmjRouting() {
    route("/vdmj") {
        get("{exp?}") {
            val exp = call.parameters["exp"] ?: return@get call.respondText(
                "Missing expression",
                status = HttpStatusCode.BadRequest,
            )

            // TODO output is empty string after first request
            val result = vdmjExecute(exp)

            call.respond(result)
        }
    }
}
