package com.myapp.spendless.feature.Setting.preference.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.feature.Setting.formatAmount
import com.myapp.spendless.feature.Setting.preference.model.AmountFormat
import com.myapp.spendless.feature.Setting.preference.model.DecimalSeparator
import com.myapp.spendless.feature.Setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.feature.Setting.preference.model.ThousandSeparator
import com.myapp.spendless.feature.Setting.replaceDecimalCommaWithDot
import com.myapp.spendless.feature.Setting.replaceDecimalDotWithComma
import com.myapp.spendless.feature.Setting.replaceThousandCommaOrSpaceWithDot
import com.myapp.spendless.feature.Setting.replaceThousandCommaWithSpace
import com.myapp.spendless.feature.Setting.replaceThousandSpaceOrDotWithComma
import com.myapp.spendless.feature.Setting.unwrapBrackets
import com.myapp.spendless.feature.Setting.wrapWithBrackets
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

    private val _uiPreferenceState = MutableStateFlow(
        PreferencesScreenState(
            priceDisplayConfig = PriceDisplayConfig(
                amountFormat = AmountFormat.WithoutBrackets,
                decimalSeparator = DecimalSeparator.Comma,
                thousandSeparator = ThousandSeparator.Comma
            )
        )
    )
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
                        priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(amountFormat = AmountFormat.WithBrackets),
                        displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.wrapWithBrackets()
                    )

                AmountFormat.WithoutBrackets -> _uiPreferenceState.value =
                    _uiPreferenceState.value.copy(
                        priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(amountFormat = AmountFormat.WithoutBrackets),
                        displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.unwrapBrackets()
                    )
            }
        }
    }

    fun changeDecimalSeperator(decimalSeparator: DecimalSeparator) {
        viewModelScope.launch {
            when (decimalSeparator) {
                DecimalSeparator.Comma -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(decimalSeparator = DecimalSeparator.Comma),
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceDecimalDotWithComma()
                )

                DecimalSeparator.Dot -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(decimalSeparator = DecimalSeparator.Dot),
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceDecimalCommaWithDot()
                )
            }
        }
    }

    fun changeThousandSeperator(thousandSeparator: ThousandSeparator) {
        viewModelScope.launch {
            when (thousandSeparator) {
                ThousandSeparator.Comma -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(thousandSeparator = ThousandSeparator.Comma),
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceThousandSpaceOrDotWithComma()
                )

                ThousandSeparator.Dot -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(thousandSeparator = ThousandSeparator.Dot),
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceThousandCommaOrSpaceWithDot()
                )

                ThousandSeparator.Space -> _uiPreferenceState.value = _uiPreferenceState.value.copy(
                    priceDisplayConfig = _uiPreferenceState.value.priceDisplayConfig.copy(thousandSeparator = ThousandSeparator.Space),
                    displayTotalAmount = _uiPreferenceState.value.displayTotalAmount.replaceThousandCommaWithSpace()
                )
            }
        }
    }

    fun updatePriceDisplayConfig(
        amountFormat: AmountFormat,
        decimalSeparator: DecimalSeparator,
        thousandSeparator: ThousandSeparator
    ) {
        val newState = _uiPreferenceState.value.copy(
            priceDisplayConfig = PriceDisplayConfig(
                amountFormat,
                decimalSeparator,
                thousandSeparator
            )
        )
        _uiPreferenceState.value = newState
    }
}