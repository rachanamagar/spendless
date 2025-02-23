package com.myapp.spendless.model

import java.util.UUID

data class User(
    val id: UUID,
    val name: String,
    val pin: String
) {
    fun User.toUpperCase(): String {
        return this.name.uppercase()
    }
}





