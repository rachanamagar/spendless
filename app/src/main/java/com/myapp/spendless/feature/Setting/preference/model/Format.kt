package com.myapp.spendless.feature.Setting.preference.model


sealed class AmountFormat {
    object WithBrackets : AmountFormat()
    object WithoutBrackets : AmountFormat()
}

sealed class DecimalSeparator {
    object Comma : DecimalSeparator()
    object Dot : DecimalSeparator()
}

sealed class ThousandSeparator {
    object Comma : ThousandSeparator()
    object Dot : ThousandSeparator()
    object Space : ThousandSeparator()
}


