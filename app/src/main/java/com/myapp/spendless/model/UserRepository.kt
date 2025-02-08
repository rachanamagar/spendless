package com.myapp.spendless.model

interface UserRepository {

    suspend fun insertUer(user: User)

    suspend fun getUserById(id: Int): User
}