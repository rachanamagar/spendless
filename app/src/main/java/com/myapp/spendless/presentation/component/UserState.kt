package com.myapp.spendless.presentation.component

import com.myapp.spendless.model.User
import java.util.UUID

data class UserState(
    val user: User = User(
        id = UUID.randomUUID(),
        name = "",
        pin = ""
    ),
    val isValidUser: Boolean = false,
    val isExistingUser: Boolean = false
)

