package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.ApiResponse
import io.github.ilyaskerbal.domain.model.EndPoint
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authorizedRoute() {
    route(EndPoint.Authorized.path) {
        authenticate("auth-session") {
            get {
                call.respond(
                    message = ApiResponse(success = true),
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}