package io.github.ilyaskerbal.domain.model

import io.ktor.server.auth.*

data class UserSession(
    val id: String,
    val name: String,
    val email: String
) : Principal
