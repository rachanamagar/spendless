package com.myapp.spendless.data

import com.myapp.spendless.data.LocalData.SpendLessDao
import com.myapp.spendless.model.User
import com.myapp.spendless.model.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun getUserById(id: Int): User {
       return dao.getUserById(id).toUserModel()
    }

}