package com.myapp.spendless.presentation.setting

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.myapp.spendless.ui.theme.Success
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun String.toIncomeUnit(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = Success)) {
            append("$")
        }
        append(this@toIncomeUnit)
    }
}


fun String.toExpensesUnit(color: Color): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = color)) {
            append(" -$")
        }
        append(this@toExpensesUnit)
    }
}

fun String.toFormatUnit(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle()) {
            append(" -($ $this )")
        }
        append(this@toFormatUnit)
    }
}

fun String.toDollar(): String {
    return "$ $this"
}

fun formatAmountToFormatUnit(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: return "Invalid amount"
    return "($parsedAmount)"
}

fun formatAmount(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: return "Invalid amount"
    val formatted = DecimalFormat("#,###.##", DecimalFormatSymbols(Locale.US))
    return formatted.format(parsedAmount)
}

fun formatThousFormat(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: return "Invalid amount"
    val formatted = DecimalFormat("#.###", DecimalFormatSymbols(Locale.GERMANY))
    return formatted.format(parsedAmount)
}

fun formatDecimalCommaFormat(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: return "Invalid amount"
    val symbols = DecimalFormatSymbols(Locale.GERMANY).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val formatted = DecimalFormat("#,###.##", symbols)
    return formatted.format(parsedAmount)
}

fun formatThousandSpaceFormat(amount: String): String {
    val parsedAmount = amount.toDoubleOrNull() ?: return "Invalid amount"
    val symbols = DecimalFormatSymbols(Locale.FRANCE).apply {
        groupingSeparator = ' '
    }
    val formatted = DecimalFormat("#,###.##", symbols)
    return formatted.format(parsedAmount)
}

fun String.wrapWithBrackets(): String {
    return "($this)"
}

fun String.unwrapBrackets(): String {
    return this.removePrefix("(").removeSuffix(")")
}

fun String.replaceDecimalDotWithComma(): String {
    val hasBrackets = this.startsWith("(") && this.endsWith(")")
    val coreNumber = this.removeSurrounding("(", ")") // Extract number part

    val lastDotIndex = coreNumber.lastIndexOf('.')
    val modifiedNumber = if (lastDotIndex != -1) {
        coreNumber.replaceRange(lastDotIndex, lastDotIndex + 1, ",")
    } else {
        coreNumber
    }

    return if (hasBrackets) "($modifiedNumber)" else modifiedNumber
}

fun String.replaceDecimalCommaWithDot(): String {
    val hasBrackets = this.startsWith("(") && this.endsWith(")")
    val coreNumber = this.removeSurrounding("(", ")")

    val lastCommaIndex = coreNumber.lastIndexOf(',')
    val modifiedNumber = if (lastCommaIndex != -1) {
        coreNumber.replaceRange(lastCommaIndex, lastCommaIndex + 1, ".")
    } else {
        coreNumber
    }

    return if (hasBrackets) "($modifiedNumber)" else modifiedNumber
}

fun String.replaceThousandCommaWithSpace(): String {
    val hasBrackets = this.startsWith("(") && this.endsWith(")")
    val coreNumber = this.removeSurrounding("(", ")")

    // Replace only thousand separator commas (not decimal commas)
    val modifiedNumber = coreNumber.replace(Regex("(?<=\\d)[,.](?=\\d{3})"), " ")

    return if (hasBrackets) "($modifiedNumber)" else modifiedNumber
}

fun String.replaceThousandCommaOrSpaceWithDot(): String {
    val hasBrackets = this.startsWith("(") && this.endsWith(")")
    val coreNumber = this.removeSurrounding("(", ")")

    // Replace only thousand separator comma (`,`) or space with dot (`.`)
    val modifiedNumber = coreNumber.replace(Regex("(?<=\\d)[, ](?=\\d{3})"), ".")

    return if (hasBrackets) "($modifiedNumber)" else modifiedNumber
}


fun String.replaceThousandSpaceOrDotWithComma(): String {
    val hasBrackets = this.startsWith("(") && this.endsWith(")")
    val coreNumber = this.removeSurrounding("(", ")")

    // Replace space or dot only when it's used as a thousand separator
    val modifiedNumber = coreNumber.replace(Regex("(?<=\\d)([ .])(?=\\d{3})"), ",")

    return if (hasBrackets) "($modifiedNumber)" else modifiedNumber
}

fun String.addCommaToThousands(): String {
    val number = this.toLongOrNull() ?: return this

    val formattedNumber = DecimalFormat("#,###").format(number)
    return formattedNumber
}