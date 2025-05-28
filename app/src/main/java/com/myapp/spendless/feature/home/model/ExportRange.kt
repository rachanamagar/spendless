package com.myapp.spendless.feature.home.model

enum class ExportRange(val rangeName: String) {
    LAST_THREE_MONTH("Last three month"),
    CURRENT_MONTH("Current month"),
    LAST_MONTH("Last month"),
    SPECIFIC_MONTH("Specific month")
}