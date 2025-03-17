package com.myapp.spendless.feature.Registration.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.feature.Registration.model.UserRepository
import com.myapp.spendless.feature.Registration.presentation.state.UserState
import com.myapp.spendless.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserViewmodel @Inject constructor(
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _uiState


     fun onAction(action: UserAction) {
        when (action) {
            is UserAction.GetUsername -> getUsername(action.username)
            is UserAction.GetPin -> getPin(action.pin)
            is UserAction.InsertUser -> insertUser()
            is UserAction.ValidateUser -> validateUser(action.name, action.pin, action.onResult)
            is UserAction.LogOutUser -> logOutUser()
            is UserAction.SaveUserName -> saveUserName(action.userName)
            is UserAction.GetSessionUserName -> getUsername(action.userName)
            is UserAction.ExistingUser -> isExistingUser(action.name)
        }
    }

    private fun isExistingUser(name: String) {
        viewModelScope.launch {
            val result = repository.getAllUser()
                .first()
                .any { it.name == name }

            _uiState.value = _uiState.value.copy(
                isExistingUser = result
            )
            return@launch
        }
    }


    private fun getUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(name = username)
        )
    }

    private fun getPin(pin: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(pin = pin)
        )
    }

    private fun insertUser() {
        val newUser = _uiState.value.user
        viewModelScope.launch {
            repository.insertUser(newUser)
        }
    }

    private fun validateUser(name: String, pin: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByName(name)
            if (user != null && user.pin == pin) {
                sessionManager.saveUserSession(user.id)
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun logOutUser() {
        viewModelScope.launch {
            sessionManager.clearUserSession()
        }
    }

    private fun saveUserName(userName: String) {
        viewModelScope.launch {
            sessionManager.saveUserName(userName)
        }
    }

    fun getUserName() {
        viewModelScope.launch {
            sessionManager.getUserName()
        }
    }
}