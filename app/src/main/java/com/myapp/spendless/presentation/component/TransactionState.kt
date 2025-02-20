package com.myapp.spendless.presentation.component

import com.myapp.spendless.R
import com.myapp.spendless.model.Transaction

data class TransactionState(
    val transaction: Transaction = Transaction(
        id = 0,
        title = "",
        amount = 1.0,
        note = "",
        category = "Income",
        icon = R.drawable.income,
        date = 1
    ),
    val transactionList:List<Transaction> =  emptyList(),
    val totalAmount: Double = 0.0,
    val maxTransaction: Transaction? = null,
    val lastWeek: Double = 0.0
)
