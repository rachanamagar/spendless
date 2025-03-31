package com.myapp.spendless.feature.Setting.security

import com.myapp.spendless.feature.Setting.model.Duration
import com.myapp.spendless.feature.Setting.model.SessionExpiry

data class sessionExpiryState(
    val expiryDuration: Duration = Duration(
        duration = SessionExpiry.FirstDuration
    ),
    val isSessionExpire: Boolean= false
)
