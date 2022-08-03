package io.github.ilyaskerbal.routes

import io.github.ilyaskerbal.domain.model.ApiResponse
import io.github.ilyaskerbal.domain.model.EndPoint
import io.github.ilyaskerbal.domain.model.UserSession
import io.github.ilyaskerbal.domain.model.UserUpdateRequest
import io.github.ilyaskerbal.domain.repository.UserDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUserRoute(
    userDataSource: UserDataSource
) {
    route(EndPoint.UpdateUser.path) {
        authenticate("auth-session") {
            put {
                val userSession = call.principal<UserSession>()
                if (userSession != null) {
                    try {
                       val updateRequest = try {
                           val result = call.receive<UserUpdateRequest>()
                           val firstName = result.firstName.trim()
                           val lastName = result.lastName.trim()
                           if (firstName.isBlank() || lastName.isBlank() || firstName.length <= 2 || lastName.length <= 2) {
                               call.respond(
                                   message = ApiResponse(
                                       success = false,
                                       message = "Invalid parameters"
                                   ),
                                   status = HttpStatusCode.BadRequest
                               )
                               return@put
                           }else{
                               result.copy(firstName = firstName, lastName = lastName)
                           }
                       } catch (e: Exception) {
                           call.respond(
                               message = ApiResponse(
                                   success = false,
                                   message = "Invalid request"
                               ),
                               status = HttpStatusCode.BadRequest
                           )
                           return@put
                       }
                        val updateStatus = userDataSource.updateUserInfo(
                            userEmail = userSession.email,
                            firstName = updateRequest.firstName,
                            lastName = updateRequest.lastName
                        )
                        if (updateStatus) {
                            call.respond(
                                message = ApiResponse(
                                    success = true,
                                    user = userDataSource.getUserInfo(userSession.email)?.copy(_id = null),
                                    message = "Update successful"
                                )
                            )
                        } else {
                            call.respond(
                                message = ApiResponse(
                                    success = false,
                                    user = null,
                                    message = "Unable to update"
                                ),
                                status = HttpStatusCode.BadRequest
                            )
                        }

                    } catch (e: Exception) {
                        call.respondRedirect(EndPoint.Unauthorized.path)
                    }
                } else {
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            }
        }
    }
}