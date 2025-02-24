package com.myapp.spendless.presentation.setting.preference.viewModel

import com.myapp.spendless.presentation.setting.preference.model.AmountFormat
import com.myapp.spendless.presentation.setting.preference.model.DecimalSeparator
import com.myapp.spendless.presentation.setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.presentation.setting.preference.model.ThousandSeparator

data class PreferencesScreenState(
    val priceDisplayConfig: PriceDisplayConfig = PriceDisplayConfig(
        amountFormat = AmountFormat.WithoutBrackets,
        decimalSeparator = DecimalSeparator.Comma,
        thousandSeparator = ThousandSeparator.Comma
    ),
    val displayTotalAmount: String = "0"
)
