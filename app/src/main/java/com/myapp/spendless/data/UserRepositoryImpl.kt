package com.myapp.spendless.data

import com.myapp.spendless.data.LocalData.SpendLessDao
import com.myapp.spendless.model.User
import com.myapp.spendless.model.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: SpendLessDao): UserRepository {
    override suspend fun insertUser(user: User) {
        dao.insert(user.toEntity())
    }

    override suspend fun getAllUser(): Flow<Set<User>> {
       return dao.getAllUser().map { users ->
           users.map { it.toUserModel() }.toSet()
       }
    }

    override suspend fun getUserByName(userName: String): User? {
        return dao.getUserByName(userName)?.toUserModel()
    }

    override suspend fun isUserValid(userName: String, pin: String): Boolean {
       val user = dao.getUserByName(userName)
        return user?.pin == pin
    }

    override suspend fun getUserIDIfValid(username: String, pin: String): UUID? {
        val user = dao.getUserByName(username)
        return user?.takeIf { it.pin == pin }?.id
    }
}