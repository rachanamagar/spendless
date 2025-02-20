package com.myapp.spendless.data

import com.myapp.spendless.data.LocalData.TransactionEntity
import com.myapp.spendless.data.LocalData.UserEntity
import com.myapp.spendless.model.Transaction
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

fun TransactionEntity.toTransactionModel(): Transaction{
    return Transaction(
        id = id,
        title = title,
        note = note,
        amount = amount,
        category = category,
        icon = icon,
        date = date
    )
}

fun Transaction.toTransEntity(): TransactionEntity{
    return TransactionEntity(
        id = id,
        title = title,
        note = note,
        amount = amount,
        category = category,
        icon = icon,
        date = date
    )
}