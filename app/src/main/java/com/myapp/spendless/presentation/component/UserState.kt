package com.myapp.spendless.presentation.component

import com.myapp.spendless.model.User

data class UserState(
    val user: User = User(
        id = 0,
        name = "",
        pin = "12345",
        confirmedPin = "12345"
    )
)
