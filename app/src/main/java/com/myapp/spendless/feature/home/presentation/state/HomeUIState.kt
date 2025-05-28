package com.myapp.spendless.feature.home.presentation.state

import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.feature.home.model.Transaction
import java.util.UUID

data class HomeUiState(
    val totalAmount: Double = 0.00,
    val lastWeekTransactionAmount: Double = 0.00,
    val maxTransaction: Transaction? = Transaction(
        id = 0,
        title = "",
        amount = 1.0,
        note = "",
        category = "Income",
        icon = R.drawable.income,
        date = 1,
        userId = UUID.randomUUID()
    ),
    val transactionList: List<Transaction> = emptyList(),
    val username: String? = null,
    val currency: String? = null,
    val popularCategory: String? = null,
    val priceDisplayConfig: PriceDisplayConfig? = null
)
