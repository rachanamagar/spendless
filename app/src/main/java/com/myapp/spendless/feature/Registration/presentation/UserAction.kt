package com.myapp.spendless.feature.Registration.presentation

sealed class UserAction {
    data class GetUsername(val username: String) : UserAction()
    data class GetPin(val pin: String) : UserAction()
    object InsertUser : UserAction()
    data class ValidateUser(val name: String, val pin: String, val onResult: (Boolean) -> Unit) : UserAction()
    object LogOutUser : UserAction()
    data class SaveUserName(val userName: String) : UserAction()
    data class GetSessionUserName(val userName :String): UserAction()
    data class ExistingUser(val name: String) : UserAction()
}