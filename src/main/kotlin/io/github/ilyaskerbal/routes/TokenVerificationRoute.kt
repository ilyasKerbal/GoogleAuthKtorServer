package io.github.ilyaskerbal.routes

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.ilyaskerbal.domain.model.ApiRequest
import io.github.ilyaskerbal.domain.model.EndPoint
import io.github.ilyaskerbal.domain.model.UserSession
import io.github.ilyaskerbal.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Route.tokenVerificationRoute() {
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
                    val name = verifiedToken.payload["name"].toString()
                    val email = verifiedToken.payload["email"].toString()
                    call.sessions.set(UserSession(id = "12", "Elyas Eagle"))
                    call.respondRedirect(EndPoint.Authorized.path)
                } else {
                    call.respondRedirect(EndPoint.Unauthorized.path)
                }
            } else {
                call.respondRedirect(EndPoint.Unauthorized.path)
            }
        }
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