package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.ApiResponse
import io.github.ilyaskerbal.domain.model.EndPoint
import io.github.ilyaskerbal.domain.model.UserSession
import io.github.ilyaskerbal.domain.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserInfoRoute(
    userDataSource: UserDataSource
) {
    route(EndPoint.GetUser.path) {
        authenticate("auth-session"){
            get {
                val userSession = call.principal<UserSession>()
                if (userSession == null){
                    call.respondRedirect(EndPoint.Unauthorized.path)
                } else {
                    try {
                        call.respond(
                            message = ApiResponse(
                                success = true,
                                user = userDataSource.getUserInfo(userSession.email)?.copy(_id = null)
                            ),
                            status = HttpStatusCode.OK
                        )
                    } catch (e: Exception) {
                        call.respondRedirect(EndPoint.Unauthorized.path)
                    }
                }
            }
        }
    }
}