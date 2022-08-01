package io.github.ilyaskerbal.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val firstName: String,
    val lastName: String
)
