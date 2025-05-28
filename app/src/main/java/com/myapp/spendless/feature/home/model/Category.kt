package com.myapp.spendless.feature.home.model

import com.myapp.spendless.R

enum class Category(val categoryName: String, val iconResId: Int) {
    FOOD_AND_BEVERAGE("Food & Beverage", R.drawable.food),
    EDUCATION("Education", R.drawable.education),
    CLOTHING_AND_ACCESSORIES("Clothing & Accessories", R.drawable.clothing),
    ENTERTAINMENT("Entertainment", R.drawable.entertain),
    HEALTH_AND_WELLNESS("Health & Wellness", R.drawable.health),
    TRANSPORT("Transport", R.drawable.transport),
    PERSONAL_CARE("Personal Care", R.drawable.personalcare),
    HOME("Home", R.drawable.home),
    INCOME("Income", R.drawable.income),
}

