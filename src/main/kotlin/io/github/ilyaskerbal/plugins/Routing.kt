package io.github.ilyaskerbal.plugins

import io.github.ilyaskerbal.domain.repository.UserDataSource
import io.github.ilyaskerbal.routes.authorizedRoute
import io.github.ilyaskerbal.routes.rootRoute
import io.github.ilyaskerbal.routes.tokenVerificationRoute
import io.github.ilyaskerbal.routes.unauthorizedRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    routing {

        val userDataSource by inject<UserDataSource>()
        // val userDataSource: UserDataSource by inject<UserDataSource>()

        authorizedRoute()
        rootRoute()
        unauthorizedRoute()
        tokenVerificationRoute(userDataSource)
    }
}
