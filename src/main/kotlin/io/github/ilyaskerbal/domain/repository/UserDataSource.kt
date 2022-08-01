package io.github.ilyaskerbal.domain.repository

import io.github.ilyaskerbal.domain.model.User

interface UserDataSource {
    suspend fun getUserInfo(userEmail: String): User?
    suspend fun saveUserInfo(user: User): Boolean
    suspend fun deleteUser(userEmail: String): Boolean
    suspend fun updateUserInfo(
        userEmail: String,
        firstName: String,
        lastName: String
    ): Boolean
}