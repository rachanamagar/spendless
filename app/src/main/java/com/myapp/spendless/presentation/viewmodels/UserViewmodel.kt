package com.myapp.spendless.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.myapp.spendless.presentation.component.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewmodel: ViewModel() {

    private val _uiState = MutableStateFlow(UserState())
    val state:StateFlow<UserState> = _uiState

    fun getUsername(username: String){
        _uiState.value = _uiState.value.copy(
            user = _uiState.value.user.copy(
                name = username
            )
        )

    }
}