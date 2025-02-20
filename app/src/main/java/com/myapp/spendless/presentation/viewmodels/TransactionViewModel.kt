package com.myapp.spendless.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.model.Transaction
import com.myapp.spendless.model.TransactionRepository
import com.myapp.spendless.presentation.component.TransactionEvent
import com.myapp.spendless.presentation.component.TransactionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionState())
    val uiState: StateFlow<TransactionState> = _uiState

    init {
        getAllTransaction()
        totalBalance()
        showLargestAmount()
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

    fun insertTransaction() {

        val date = System.currentTimeMillis()
        _uiState.value = _uiState.value.copy(
           transaction = _uiState.value.transaction.copy(
               date = date
           )
        )
        val transaction = _uiState.value.transaction
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    fun getAllTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTransaction()
                .catch { e -> println(e.localizedMessage ?: "An error occured.") }
                .collect { list ->
                    _uiState.value = _uiState.value.copy(
                        transactionList = list
                    )
                }
        }
    }

    fun totalBalance() {
        viewModelScope.launch {
            repository.getTransaction().map { transaction ->
                transaction.sumOf {
                    if (it.category == "Income") it.amount else -it.amount
                }
            }
                .collect { total ->
                    _uiState.update { it.copy(totalAmount = total)
                    }
                }
        }
    }

    fun showLargestAmount(){
        viewModelScope.launch {
            repository.getTransaction().collect { transactionList ->
                val filteredTransaction = transactionList.filter { it.category != "Income" }
                val largestTransaction = filteredTransaction.maxByOrNull { it.amount }

                val currentTime = System.currentTimeMillis()
                val lastWeekTransaction = filteredTransaction.filter {
                    val tranDate = it.date
                    val lastSevenDaysTransaction = currentTime - (7*24*60*60*1000)
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

}