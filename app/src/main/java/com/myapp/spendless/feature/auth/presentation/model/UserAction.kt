package com.myapp.spendless.feature.auth.presentation.model

sealed class UserAction {
    data class GetPin(val pin: String) : UserAction()
    data object InsertUser : UserAction()
    data class ValidateUser(val name: String, val pin: String) : UserAction()
    data object LogOutUser : UserAction()
    data class GetSessionUserName(val userName :String): UserAction()
    data class ExistingUser(val name: String) : UserAction()
}