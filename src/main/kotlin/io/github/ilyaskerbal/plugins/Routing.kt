package io.github.ilyaskerbal.plugins

import io.github.ilyaskerbal.routes.rootRoute
import io.github.ilyaskerbal.routes.unauthorizedRoute
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {
        rootRoute()
        unauthorizedRoute()
    }
}
