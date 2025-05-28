package com.myapp.spendless.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.feature.home.model.TransactionRepository
import com.myapp.spendless.feature.home.presentation.state.TransactionEvent
import com.myapp.spendless.feature.home.presentation.state.TransactionState
import com.myapp.spendless.util.DataStoreManager
import com.myapp.spendless.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val sessionManager: SessionManager,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionState())
    val uiState: StateFlow<TransactionState> = _uiState

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
       _uiState.update {
           it.copy(transaction = it.transaction.copy(title = title))
       }
    }

    private fun updateNote(note: String) {
        _uiState.update {
            it.copy(
                transaction = it.transaction.copy(
                    note = note
                )
            )
        }
    }

    private fun updateAmount(amount: Double) {
        _uiState.update {
            it.copy(
                transaction = it.transaction.copy(
                    amount = amount
                )
            )
        }
    }

    private fun updateCategory(category: String) {
        _uiState.update {
            it.copy(
                transaction = it.transaction.copy(
                    category = category
                )
            )
        }
    }

    private fun updateIcon(icon: Int) {
        _uiState.update {
            it.copy(
                transaction = it.transaction.copy(
                    icon = icon
                )
            )
        }
    }

    fun changeSymbol(symbol: String) {
        _uiState.update {
            it.copy(symbol = symbol)
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

    fun resetState() {
        _uiState.value = TransactionState()
    }
}