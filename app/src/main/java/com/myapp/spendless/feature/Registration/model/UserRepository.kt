package com.myapp.spendless.feature.Registration.model

import com.myapp.spendless.model.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun getAllUser(): Flow<Set<User>>
    suspend fun getUserByName(userName: String): User?
    suspend fun isUserValid(userName: String, pin: String): Boolean
    suspend fun getUserIDIfValid(username: String, pin: String): UUID?
}