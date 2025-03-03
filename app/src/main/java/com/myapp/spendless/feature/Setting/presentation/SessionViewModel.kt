package com.myapp.spendless.feature.Setting.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.spendless.feature.Setting.model.Duration
import com.myapp.spendless.feature.Setting.model.SessionExpiry
import com.myapp.spendless.util.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _sessionState = MutableStateFlow(sessionExpiryState())
    val sessionState: StateFlow<sessionExpiryState> = _sessionState

    fun setSessionExpiry(duration: SessionExpiry) {

        val expiryTime = when (duration) {
            SessionExpiry.FirstDuration -> 1 * 60 * 1000L
            SessionExpiry.FourthDuration -> 2 * 60 * 1000L
            SessionExpiry.SecondDuration -> 33 * 60 * 1000L
            SessionExpiry.ThirdDuration -> 60 * 60 * 1000L
    }
        viewModelScope.launch {
            dataStoreManager.setSessionExpiry(duration = expiryTime)
            _sessionState.value = _sessionState.value.copy(
                expiryDuration = Duration(duration = duration)
            )
            delay(expiryTime)
            _sessionState.value = _sessionState.value.copy(
                isSessionExpire = true
            )
            Log.d("SessionViewModel", "User session expired after $expiryTime ms")
        }
    }
}
