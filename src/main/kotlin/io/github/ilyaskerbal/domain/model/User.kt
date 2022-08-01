package io.github.ilyaskerbal.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class User(
    @Contextual val _id: Id<User> = newId(),
    val googleId: String,
    val name: String,
    val email: String,
    val profilePhoto: String
)
