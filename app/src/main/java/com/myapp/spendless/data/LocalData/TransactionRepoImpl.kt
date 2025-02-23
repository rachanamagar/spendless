package com.myapp.spendless.data.LocalData

import com.myapp.spendless.data.toTransEntity
import com.myapp.spendless.data.toTransactionModel
import com.myapp.spendless.model.Transaction
import com.myapp.spendless.model.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class TransactionRepoImpl @Inject constructor(private val dao: TransactionDao): TransactionRepository {

    override suspend fun insertTransaction(transaction: Transaction, userId: UUID) {
        return dao.insert(transaction.copy(userId = userId).toTransEntity())
    }

    override suspend fun getTransaction(userId:UUID): Flow<List<Transaction>> =
         dao.getAllTransaction().map { list ->
             list.filter { it.userId == userId }
                 .map { it.toTransactionModel() }
         }
}