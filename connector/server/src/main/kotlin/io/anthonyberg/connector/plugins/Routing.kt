package io.anthonyberg.connector.plugins

import io.anthonyberg.connector.routes.vdmjRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        vdmjRouting()
    }
}
