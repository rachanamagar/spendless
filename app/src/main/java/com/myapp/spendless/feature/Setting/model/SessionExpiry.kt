package com.myapp.spendless.feature.Setting.model

sealed class SessionExpiry {
    object FirstDuration: SessionExpiry()
    object SecondDuration: SessionExpiry()
    object ThirdDuration: SessionExpiry()
    object FourthDuration: SessionExpiry()
}

data class Duration(
    val duration: SessionExpiry
)