package com.myapp.spendless.data

import com.myapp.spendless.data.LocalData.UserEntity
import com.myapp.spendless.model.User

fun UserEntity.toUserModel(): User{
    return User(
        id = id,
        name = name,
        pin = pin
    )
}

fun User.toEntity(): UserEntity{
    return UserEntity(
        id = id,
        name = name,
        pin = pin
    )
}