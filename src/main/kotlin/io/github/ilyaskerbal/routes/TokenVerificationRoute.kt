package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.EndPoint
import io.github.ilyaskerbal.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.tokenVerificationRoute() {
    route(EndPoint.TokenVerification.path) {
        post {
            call.sessions.set(UserSession(id = "12", "Elyas Eagle"))
            call.respondRedirect(EndPoint.Authorized.path)
        }
    }
}