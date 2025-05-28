package com.myapp.spendless.util

import java.text.NumberFormat
import java.util.Locale


fun String?.toCurrencySymbol(): String{
    return this ?: "$"
}
fun Double.formatCurrency(): String {
    val absValue = kotlin.math.abs(this)
    val formattedValue = String.format(Locale.US, "%.2f", absValue)

    return if (this < 0) "- $${formattedValue}" else "$${formattedValue}"
}

fun Double.formatToTwoDecimal(): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
        isGroupingUsed = true
    }
    return formatter.format(this)
}