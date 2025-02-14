package com.myapp.spendless.model

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun getAllUser(): Flow<Set<User>>
    suspend fun getUserByName(userName: String): User?
    suspend fun isUserValid(userName: String, pin: String): Boolean


}