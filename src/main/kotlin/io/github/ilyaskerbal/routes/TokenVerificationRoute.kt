package io.github.ilyaskerbal.routes

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.ilyaskerbal.domain.model.*
import io.github.ilyaskerbal.domain.repository.UserDataSource
import io.github.ilyaskerbal.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.util.pipeline.*

fun Route.tokenVerificationRoute(
    userDataSource: UserDataSource
) {
    route(EndPoint.TokenVerification.path) {
        post {
            val request = try {
                call.receive<ApiRequest>()
            } catch (e: Exception) {
                call.respondRedirect(EndPoint.Unauthorized.path)
                return@post
            }
            if (!request.tokenId.isBlank()) {
                val verifiedToken = verifyTokenId(request.tokenId)
                if (verifiedToken != null) {
                    saveUserToDatabase(
                        verifiedToken = verifiedToken,
                        userDataSource = userDataSource
                    )
                } else {
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            } else {
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        }
    }
}

private suspend fun PipelineContext<Unit, ApplicationCall>.saveUserToDatabase(
    verifiedToken: GoogleIdToken,
    userDataSource: UserDataSource
) {
    val sub = verifiedToken.payload["sub"].toString()
    val name = verifiedToken.payload["name"].toString()
    val email = verifiedToken.payload["email"].toString()
    val picture = verifiedToken.payload["picture"].toString()
    val newUser = User(
        googleId = sub,
        name = name,
        email = email,
        profilePhoto = picture
    )
    val saveResponse = userDataSource.saveUserInfo(newUser)
    println(saveResponse)
    if (saveResponse) {
        call.sessions.set(UserSession(id = sub, name =  name, email = email))
        call.respond(
            message = ApiResponse(
                success = true,
                user = userDataSource.getUserInfo(email)?.copy(_id = null),
                message = "Sign in operation successful"
            ),
            status = HttpStatusCode.OK
        )
    } else {
        call.respondRedirect(EndPoint.Unauthorized.path)
    }

}

fun verifyTokenId(tokenId: String): GoogleIdToken? {
    return try {
        val verifier = GoogleIdTokenVerifier
            .Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(Constants.CLIENT_ID))
            .setIssuer(Constants.ISSUER)
            .build()
        verifier.verify(tokenId)
    } catch (e: Exception) {
        return null
    }
}