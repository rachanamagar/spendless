package com.myapp.spendless.data.LocalData

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val pin: String
)

