package com.myapp.spendless.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.model.UserRepository
import com.myapp.spendless.presentation.component.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewmodel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _uiState

    fun getUsername(username: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(
                name = username
            )
        )
    }

    fun getPin(pin: String) {
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(
                pin = pin
            )
        )
    }

    fun insertUser() {
        val newUser = _uiState.value.user
        viewModelScope.launch {
            repository.insertUser(newUser)
        }
    }
    fun existingUser(username: String){
        val user =_uiState.value.user
      /*  viewModelScope.launch {
            val list = repository.getAllUser()
            val user = list.first().any{it.name == username}

            if (user)

        }*/
    }
}