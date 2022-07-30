package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.unauthorizedRoute() {
    route(EndPoint.Unauthorized.path) {
        get {
            call.respond(
                message = "Not authorized",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
}