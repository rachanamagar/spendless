package com.myapp.spendless.feature.Setting.preference.model

data class PriceDisplayConfig(
    val amountFormat: AmountFormat,
    val decimalSeparator: DecimalSeparator,
    val thousandSeparator: ThousandSeparator
)
