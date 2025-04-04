package com.myapp.spendless.feature.HomeScreen.model

import com.myapp.spendless.R
import java.util.UUID

data class Transaction(
    val userId : UUID,
    val id: Int,
    val title: String,
    val amount: Double,
    val note: String,
    val category: String,
    val icon: Int,
    val date: Long
)

data class Categories(
    val category: String,
    val icon: Int
)

val categories = listOf(
    Categories( "Food & Beverage", R.drawable.food),
    Categories( "Education", R.drawable.education),
    Categories( "Clothing & Accessories", R.drawable.clothing),
    Categories( "Entertainment", R.drawable.entertain),
    Categories( "Health & Wellness", R.drawable.health),
    Categories( "Transport", R.drawable.transport),
    Categories( "Personal Care", R.drawable.personalcare),
    Categories( "Home", R.drawable.home),
    Categories( "Income", R.drawable.income),
)
