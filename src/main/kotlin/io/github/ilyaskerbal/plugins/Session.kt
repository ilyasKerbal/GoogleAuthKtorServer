package io.github.ilyaskerbal.plugins

import io.github.ilyaskerbal.domain.model.UserSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File

fun Application.configureSession() {
    install(Sessions){
        val secretEncryptKey = hex("7ef126cc8f13165b2f2483d339d9b6b3")
        val secretAuthKey = hex("adab13358046f3cde6782a2b1f56355d")
        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(rootDir = File(".sessions"))
        ) {
            //TODO: cookie.secure = true
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }
}