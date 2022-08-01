package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.ApiResponse
import io.github.ilyaskerbal.domain.model.EndPoint
import io.github.ilyaskerbal.domain.model.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.signOutRoute() {
    route(EndPoint.SignOut.path) {
        authenticate("auth-session") {
            get {
                call.sessions.clear<UserSession>()
                call.respond(
                    message = ApiResponse(
                        success = true
                    ),
                    status = HttpStatusCode.OK
                )
            }
        }
    }
}