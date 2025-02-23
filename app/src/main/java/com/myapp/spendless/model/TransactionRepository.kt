package com.myapp.spendless.model

import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface TransactionRepository {

    suspend fun insertTransaction(transaction: Transaction, userId:UUID)

    suspend fun getTransaction(userId: UUID): Flow<List<Transaction>>

}