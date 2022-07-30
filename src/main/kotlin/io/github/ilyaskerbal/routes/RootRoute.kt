package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.EndPoint
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.rootRoute() {
    route(EndPoint.Root.path) {
        get {
            call.respondText("Hello World!")
        }
    }
}