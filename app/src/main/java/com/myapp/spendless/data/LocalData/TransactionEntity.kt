package com.myapp.spendless.data.LocalData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val note: String,
    val category: String,
    val icon: Int,
    val date: Long
)
