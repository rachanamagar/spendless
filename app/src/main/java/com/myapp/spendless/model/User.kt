package com.myapp.spendless.model



data class User(
    val id: Int,
    val name: String,
    val pin: String
) {
    fun User.toUpperCase(): String {
        return this.name.uppercase()
    }
}


