package com.myapp.spendless.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.R
import com.myapp.spendless.feature.Setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.feature.home.model.Transaction
import com.myapp.spendless.feature.home.model.TransactionRepository
import com.myapp.spendless.util.DataStoreManager
import com.myapp.spendless.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val sessionManager: SessionManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        HomeUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val userTransactionFlow: StateFlow<List<Transaction>> = sessionManager.getUserSession()
        .map { userId ->
            userId?.let { repository.getTransaction(it) } ?: flowOf(emptyList())
        }
        .flatMapLatest { it }
        .catch { e -> println(e.localizedMessage ?: "An error occurred.") }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        // Combine data from both repositories
        viewModelScope.launch {
            combine(
                userTransactionFlow,
                sessionManager.getUserName(),
                dataStoreManager.getCurrencySymbol(),
                dataStoreManager.getPreferenceConfig()
            ) { transactions, userName, currency, priceDisplayConfig ->
                HomeUiState(
                    totalAmount = getTotalAmount(),
                    lastWeekTransactionAmount = getLastWeekTotalAmount(),
                    transactionList = transactions,
                    maxTransaction = userTransactionFlow.value
                        .asSequence()
                        .filter { it.category != "Income" }
                        .maxByOrNull { it.amount },
                    popularCategory = getPopularCategory(transactions),
                    username = userName ?: "",
                    currency = currency,
                    priceDisplayConfig = priceDisplayConfig
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    private fun getTotalAmount() = userTransactionFlow.value.sumOf {
        if (it.category == "Income") it.amount else -it.amount
    }

    private fun getPopularCategory(transactions: List<Transaction>) =
        transactions
            .asSequence()
            .filter { it.category != "Income" }
            .groupingBy { it.category }
            .eachCount()
            .entries
            .groupBy { it.value }
            .maxByOrNull { it.key }
            ?.value
            ?.singleOrNull()
            ?.key

    private suspend fun getLastWeekTotalAmount() = (userTransactionFlow.firstOrNull()
        ?.asSequence()
        ?.filter {
            it.date < System.currentTimeMillis() - TimeUnit.DAYS.toMillis(
                7
            )
        }
        ?.sumOf { if (it.category == "Income") it.amount else -it.amount }
        ?.let { abs(it) }
        ?: 0.0)

}


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
