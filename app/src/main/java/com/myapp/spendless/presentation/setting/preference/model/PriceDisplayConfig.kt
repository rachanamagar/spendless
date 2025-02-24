package com.myapp.spendless.presentation.setting.preference.model

data class PriceDisplayConfig(
    val amountFormat: AmountFormat,
    val decimalSeparator: DecimalSeparator,
    val thousandSeparator: ThousandSeparator
)
