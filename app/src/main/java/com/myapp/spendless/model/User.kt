package com.myapp.spendless.model

data class User(
    val id: Int,
    val name: String,
    val pin: String,
    val confirmedPin: String
)
