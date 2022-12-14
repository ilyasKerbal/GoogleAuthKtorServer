package io.github.ilyaskerbal.data.repository

import io.github.ilyaskerbal.domain.model.User
import io.github.ilyaskerbal.domain.repository.UserDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class UserDataSourceImpl(
    private val database: CoroutineDatabase
) : UserDataSource {

    private val users = database.getCollection<User>()

    override suspend fun getUserInfo(email: String): User? {
        return users.findOne(User::email eq email)
    }

    override suspend fun saveUserInfo(user: User): Boolean {
        val userExits = getUserInfo(user.email)
        return if (userExits == null) {
            users.save(document = user)?.wasAcknowledged() ?: false
        } else {
            // The user already exists
            true
        }
    }

    override suspend fun deleteUser(email: String): Boolean {
        return users.deleteOne(User::email eq email).wasAcknowledged()
    }

    override suspend fun updateUserInfo(
        userEmail: String,
        firstName: String,
        lastName: String): Boolean {
        return users.updateOne(
            filter = User::email eq userEmail,
            update = setValue(
                property = User::name,
                value = "$firstName $lastName"
            )
        ).wasAcknowledged()
    }
}