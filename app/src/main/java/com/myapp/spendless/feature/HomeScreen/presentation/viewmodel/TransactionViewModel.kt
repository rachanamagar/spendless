package com.myapp.spendless.feature.HomeScreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.feature.HomeScreen.model.TransactionRepository
import com.myapp.spendless.feature.HomeScreen.presentation.state.TransactionEvent
import com.myapp.spendless.feature.HomeScreen.presentation.state.TransactionState
import com.myapp.spendless.util.DataStoreManager
import com.myapp.spendless.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val sessionManager: SessionManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionState())
    val uiState: StateFlow<TransactionState> = _uiState

    private val _popularCategory = MutableStateFlow<String?>(null)
    val popularCategory = _popularCategory.asStateFlow()

    val userName: StateFlow<String?> =
        sessionManager.getUserName().stateIn(viewModelScope, SharingStarted.Lazily, null)

    init {
        getAllTransaction()
        totalBalance()
        showLargestAmount()
        showFamousCategory()
        getPreviousWeekTransaction()
    }

    fun handleEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.updateTitle -> updateTitle(event.title)
            is TransactionEvent.updateAmount -> updateAmount(event.amount)
            is TransactionEvent.updateCategory -> updateCategory(event.category)
            is TransactionEvent.updateNote -> updateNote(event.note)
            is TransactionEvent.updateIcon -> updateIcon(event.icon)
        }
    }

    private fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            transaction = _uiState.value.transaction.copy(
                title = title
            )
        )
    }

    private fun updateNote(note: String) {
        _uiState.value = _uiState.value.copy(
            transaction = _uiState.value.transaction.copy(
                note = note
            )
        )
    }

    private fun updateAmount(amount: Double) {
        _uiState.value = _uiState.value.copy(
            transaction = _uiState.value.transaction.copy(
                amount = amount
            )
        )
    }

    private fun updateCategory(category: String) {
        _uiState.value = _uiState.value.copy(
            transaction = _uiState.value.transaction.copy(
                category = category
            )
        )
    }

    private fun updateIcon(icon: Int) {
        _uiState.value = _uiState.value.copy(
            transaction = _uiState.value.transaction.copy(
                icon = icon
            )
        )
    }

    fun changeSymbol(symbol: String) {
        _uiState.value = _uiState.value.copy(
            symbol = symbol
        )
    }

    fun getPreviousWeekTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val userID = sessionManager.getUserSession().firstOrNull() ?: return@launch
            val getTransaction =
                repository.getTransaction(userId = userID).firstOrNull() ?: emptyList()

            val currentDate = System.currentTimeMillis()
            val oneWeekAgoDate = currentDate - TimeUnit.DAYS.toMillis(7)

            val sortedList = getTransaction.filter {
                it.date < oneWeekAgoDate
            }
            val totalIncome = sortedList.filter { it.category =="Income" }.sumOf { it.amount }
            val totalExpense = sortedList.filter { it.category != "Income" }.sumOf { it.amount }

            val totalAmount = totalIncome - totalExpense

            withContext(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(
                    lastWeek = abs(totalAmount)
                )
            }
        }
    }

    fun insertTransaction() {
        val date = System.currentTimeMillis()
        _uiState.value = _uiState.value.copy(
            transaction = _uiState.value.transaction.copy(
                date = date
            )
        )
        val transaction = _uiState.value.transaction
        viewModelScope.launch {
            val userID = sessionManager.getUserSession().firstOrNull() ?: return@launch

            repository.insertTransaction(transaction, userID)
        }
    }

    fun getAllTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            val userID = sessionManager.getUserSession().firstOrNull() ?: return@launch
            repository.getTransaction(userID)
                .catch { e -> println(e.localizedMessage ?: "An error occured.") }
                .collect { list ->
                    _uiState.value = _uiState.value.copy(
                        transactionList = list
                    )
                }
        }
    }

    private fun totalBalance() {
        viewModelScope.launch {
            val userID = sessionManager.getUserSession().firstOrNull() ?: return@launch
            repository.getTransaction(userID).map { transaction ->
                transaction.sumOf {
                    if (it.category == "Income") it.amount else -it.amount
                }
            }
                .collect { total ->
                    _uiState.update {
                        it.copy(totalAmount = total)
                    }
                    dataStoreManager.saveTotalAmount(total)
                }
        }
    }

    private fun showFamousCategory() {
        viewModelScope.launch {
            val userId = sessionManager.getUserSession().firstOrNull() ?: return@launch
            repository.getTransaction(userId).collect { transactions ->
                val mostFrequentCategory = transactions
                    .filter { it.category != "Income" }
                    .groupingBy { it.category }
                    .eachCount()

                val maxCount = mostFrequentCategory.values.maxOrNull()
                val popularCount = mostFrequentCategory
                    .filterValues { it == maxCount }
                    .keys

                _popularCategory.value =
                    if (popularCount.size == 1) popularCount.first() else null
            }
        }
    }

    private fun showLargestAmount() {
        viewModelScope.launch {
            val userID = sessionManager.getUserSession().firstOrNull() ?: return@launch
            repository.getTransaction(userID).collect { transactionList ->
                val filteredTransaction = transactionList.filter { it.category != "Income" }
                val largestTransaction = filteredTransaction.maxByOrNull { it.amount }

                val currentTime = System.currentTimeMillis()
                val lastWeekTransaction = filteredTransaction.filter {
                    val tranDate = it.date
                    val lastSevenDaysTransaction = currentTime - (7 * 24 * 60 * 60 * 1000)
                    tranDate >= lastSevenDaysTransaction
                }
                val lastTransaction = lastWeekTransaction.sumOf { it.amount }
                _uiState.value = _uiState.value.copy(
                    maxTransaction = largestTransaction,
                    lastWeek = lastTransaction

                )
            }
        }
    }

    fun resetState() {
        _uiState.value = TransactionState()
    }

    fun changeFormat(amount: String) {
        val cleanedAmount = amount.replace(Regex("[^\\d.]"), "")
        val amountAsDouble = cleanedAmount.toDoubleOrNull() ?: 0.0
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                totalAmount = amountAsDouble
            )
        }
    }
}