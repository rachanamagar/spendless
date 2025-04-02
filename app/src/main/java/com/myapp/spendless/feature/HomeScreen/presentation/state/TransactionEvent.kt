package com.myapp.spendless.feature.HomeScreen.presentation.state

sealed class TransactionEvent {
    data class updateTitle(val title: String): TransactionEvent()
    data class updateAmount(val amount: Double): TransactionEvent()
    data class updateNote(val note: String): TransactionEvent()
    data class updateCategory(val category: String): TransactionEvent()
    data class updateIcon(val icon: Int): TransactionEvent()
}
