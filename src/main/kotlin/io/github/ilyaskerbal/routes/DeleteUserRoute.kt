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
import io.ktor.server.sessions.*

fun Route.deleteUserRoute(
    userDataSource: UserDataSource
) {
    route(EndPoint.DeleteUser.path) {
        authenticate("auth-session") {
            delete {
                val userSession = call.principal<UserSession>()
                if (userSession != null) {
                    try {
                        val deleteStatus = userDataSource.deleteUser(
                            userEmail = userSession.email
                        )
                        if (deleteStatus) {
                            call.sessions.clear<UserSession>()
                            call.respond(
                                message = ApiResponse(
                                    success = true,
                                    message = "User deleted successfully"
                                ),
                                status = HttpStatusCode.OK
                            )
                        } else {
                            call.respond(
                                message = ApiResponse(
                                    success = false,
                                    message = "Unable to delete users, try again"
                                ),
                                status = HttpStatusCode.InternalServerError
                            )
                        }
                    } catch (e: Exception) {
                        call.respond(
                            message = ApiResponse(
                                success = false,
                                message = "Unable to delete user"
                            ),
                            status = HttpStatusCode.BadRequest
                        )
                    }
                } else {
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            }
        }
    }
}