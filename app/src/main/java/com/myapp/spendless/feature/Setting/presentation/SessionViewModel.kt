package com.myapp.spendless.feature.Setting.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.feature.Setting.model.Duration
import com.myapp.spendless.feature.Setting.model.SessionExpiry
import com.myapp.spendless.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _sessionState = MutableStateFlow(sessionExpiryState())
    val sessionState: StateFlow<sessionExpiryState> = _sessionState

    private var logOutDuration: Job? = null

    fun setSessionExpiry(duration: SessionExpiry) {
        _sessionState.value = _sessionState.value.copy(expiryDuration = Duration(duration))

        logOutDuration?.cancel()

        val expiryTime = when (duration) {
            SessionExpiry.FirstDuration -> 1 * 60 * 1000L
            SessionExpiry.FourthDuration -> 2 * 60 * 1000L
            SessionExpiry.SecondDuration -> 33 * 60 * 1000L
            SessionExpiry.ThirdDuration -> 60 * 60 * 1000L
        }
        logOutUserAfterTimeOut(expiryTime)

    }

    private fun logOutUserAfterTimeOut(time: Long) {
        logOutDuration = viewModelScope.launch(Dispatchers.IO) {
            delay(time)
            _sessionState.value = _sessionState.value.copy(
                isSessionExpire = true
            )
            sessionManager.clearUserSession()
            Log.d("SessionViewModel", "User session expired after $time ms")
        }
    }
}
