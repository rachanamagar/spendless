package com.myapp.spendless.presentation.setting.preference.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.presentation.setting.formatAmount
import com.myapp.spendless.presentation.setting.preference.model.AmountFormat
import com.myapp.spendless.presentation.setting.preference.model.DecimalSeparator
import com.myapp.spendless.presentation.setting.preference.model.ThousandSeparator
import com.myapp.spendless.presentation.setting.replaceDecimalCommaWithDot
import com.myapp.spendless.presentation.setting.replaceDecimalDotWithComma
import com.myapp.spendless.presentation.setting.replaceThousandCommaOrSpaceWithDot
import com.myapp.spendless.presentation.setting.replaceThousandCommaWithSpace
import com.myapp.spendless.presentation.setting.replaceThousandSpaceOrDotWithComma
import com.myapp.spendless.presentation.setting.unwrapBrackets
import com.myapp.spendless.presentation.setting.wrapWithBrackets
import com.myapp.spendless.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiPreferenceState = MutableStateFlow(PreferencesScreenState())
    val uiPreferenceState: StateFlow<PreferencesScreenState> = _uiPreferenceState

    init {
        getTotalAmountFromDataStore()
    }

    private fun getTotalAmountFromDataStore() {
        viewModelScope.launch {
            val amount = dataStoreManager.getTotalAmount().first()
            amount?.let {
                _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    displayTotalAmount = formatAmount(it.toString())
                )
            }
        }
    }

    fun changeAmountFormat(amountFormat: AmountFormat) {
        viewModelScope.launch {
            when (amountFormat) {
                AmountFormat.WithBrackets -> _uiPreferenceState.value =
                    _uiPreferenceState.value.copy(
                        displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.wrapWithBrackets()
                    )

                AmountFormat.WithoutBrackets -> _uiPreferenceState.value =
                    _uiPreferenceState.value.copy(
                        displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.unwrapBrackets()
                    )
            }
        }
    }

    fun changeDecimalSeperator(decimalSeparator: DecimalSeparator) {
        viewModelScope.launch {
            when (decimalSeparator) {
                DecimalSeparator.Comma -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceDecimalDotWithComma()
                )

                DecimalSeparator.Dot -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceDecimalCommaWithDot()
                )
            }
        }
    }

    fun changeThousandSeperator(thousandSeparator: ThousandSeparator) {
        viewModelScope.launch {
            when (thousandSeparator) {
                ThousandSeparator.Comma -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    displayTotalAmount =  _uiPreferenceState.value.displayTotalAmount.replaceThousandSpaceOrDotWithComma()
                )

                ThousandSeparator.Dot -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    displayTotalAmount =  _uiPreferenceState.value.displayTotalAmount.replaceThousandCommaOrSpaceWithDot()
                )

                ThousandSeparator.Space -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceThousandCommaWithSpace()
                )
            }
        }
    }
}