package com.myapp.spendless.util

import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val dataStoreManager: DataStoreManager
) {
    suspend fun saveUserSession(userId: UUID){
        dataStoreManager.saveUserId(userId)
    }

    fun getUserSession(): Flow<UUID?>{
        return dataStoreManager.getUserId()
    }

    suspend fun clearUserSession(){
        dataStoreManager.clearUserId()
    }

    suspend fun saveUserName(userName: String){
        dataStoreManager.saveUserName(userName)
    }

    fun getUserName(): Flow<String?>{
        return dataStoreManager.getUserName()
    }

}