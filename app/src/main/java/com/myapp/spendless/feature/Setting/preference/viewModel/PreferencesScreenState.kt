package com.myapp.spendless.feature.Setting.preference.viewModel

import com.myapp.spendless.feature.Setting.preference.model.AmountFormat
import com.myapp.spendless.feature.Setting.preference.model.DecimalSeparator
import com.myapp.spendless.feature.Setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.feature.Setting.preference.model.ThousandSeparator

data class PreferencesScreenState(
    val priceDisplayConfig: PriceDisplayConfig = PriceDisplayConfig(
        amountFormat = AmountFormat.WithoutBrackets,
        decimalSeparator = DecimalSeparator.Comma,
        thousandSeparator = ThousandSeparator.Comma
    ),
    val displayTotalAmount: String = "0"
)
