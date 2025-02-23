package com.myapp.spendless.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.model.UserRepository
import com.myapp.spendless.util.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class AuthenticationViewModel @Inject constructor(
    private val repository: UserRepository,
    private val dataStoreManager: DataStoreManager
): ViewModel() {

    private val _userId = MutableStateFlow<UUID?>(null)
    val userId = _userId.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.userIdFlow.collect{
                _userId.value = it
            }
        }
    }


    suspend fun loginUser(username: String, pin: String): Boolean{
        val userId = repository.getUserIDIfValid(username, pin)
        return if(userId != null){
            dataStoreManager.saveUserId(userId)
            _userId.value = userId
            true
        }else{
            false
        }
    }
    suspend fun logOutUser(){
        dataStoreManager.clearUserId()
        _userId.value = null
    }
}