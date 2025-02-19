package com.myapp.spendless.model

import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transaction: Transaction)

    suspend fun getTransaction(): Flow<List<Transaction>>

}