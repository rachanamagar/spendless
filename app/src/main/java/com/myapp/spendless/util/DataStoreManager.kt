package com.myapp.spendless.util

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.myapp.spendless.feature.Setting.preference.model.AmountFormat
import com.myapp.spendless.feature.Setting.preference.model.DecimalSeparator
import com.myapp.spendless.feature.Setting.preference.model.PriceDisplayConfig
import com.myapp.spendless.feature.Setting.preference.model.ThousandSeparator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID


val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val TOTAL_AMOUNT = doublePreferencesKey("total_amount")
        private val SESSION_EXPIRY_TIME = longPreferencesKey("session_expiry_time")

        private val AMOUNT_FORMAT = stringPreferencesKey("amount_format")
        private val DECIMAL_SEPARATOR = stringPreferencesKey("decimal_separator")
        private val THOUSAND_SEPARATOR = stringPreferencesKey("thousand_separator")
        private val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol")
    }

    private var expiryDuration: Job? = null

    suspend fun saveUserId(userId: UUID) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = userId.toString()
        }
    }

    fun getUserId(): Flow<UUID?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID]?.let { UUID.fromString(it) }
        }
    }

    suspend fun saveTotalAmount(amount: Double) {
        context.dataStore.edit { preferences ->
            preferences[TOTAL_AMOUNT] = amount
        }
    }

    fun getTotalAmount(): Flow<Double?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOTAL_AMOUNT]
        }
    }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    fun getUserName(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME]
        }
    }

    val userIdFlow: Flow<UUID?> = context.dataStore.data.map { preferences ->
        preferences[USER_ID]?.let { UUID.fromString(it) }
    }

    suspend fun setSessionExpiry(duration: Long) {
        val expiryTime = System.currentTimeMillis() + duration
        context.dataStore.edit { preferences ->
            preferences[SESSION_EXPIRY_TIME] = expiryTime
        }
        startSessionTimer(duration)
    }

    fun getSessionExpiryTime(): Flow<Long?> {
        return context.dataStore.data.map { preferences ->
            preferences[SESSION_EXPIRY_TIME]
        }
    }

    private fun startSessionTimer(duration: Long) {
        expiryDuration?.cancel()
        expiryDuration = CoroutineScope(Dispatchers.IO).launch {
            delay(duration)
            clearUserSession()
        }
    }


    suspend fun clearUserSession() {
        context.dataStore.edit { it.clear() }
    }


    suspend fun saveCurrency(currencySymbol: String) {
        context.dataStore.edit { preference ->
            preference[CURRENCY_SYMBOL] = currencySymbol
        }
    }

    fun getCurrencySymbol(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[CURRENCY_SYMBOL]
        }
    }

    suspend fun savePreferenceConfig(priceDisplayConfig: PriceDisplayConfig) {
        context.dataStore.edit { preferences ->
            preferences[AMOUNT_FORMAT] = when (priceDisplayConfig.amountFormat) {
                AmountFormat.WithBrackets -> "with_brackets"
                AmountFormat.WithoutBrackets -> "without_brackets"
            }
            preferences[DECIMAL_SEPARATOR] = when (priceDisplayConfig.decimalSeparator) {
                DecimalSeparator.Comma -> "comma"
                DecimalSeparator.Dot -> "dot"
            }
            preferences[THOUSAND_SEPARATOR] = when (priceDisplayConfig.thousandSeparator) {
                ThousandSeparator.Comma -> "comma"
                ThousandSeparator.Dot -> "dot"
                ThousandSeparator.Space -> "space"
            }
        }
    }

    fun getPreferenceConfig(): Flow<PriceDisplayConfig> {
        return context.dataStore.data.map { preferences ->
            PriceDisplayConfig(
                amountFormat = when (preferences[AMOUNT_FORMAT]) {
                    "without_brackets" -> AmountFormat.WithoutBrackets
                    else -> AmountFormat.WithBrackets
                },
                decimalSeparator = when (preferences[DECIMAL_SEPARATOR]) {
                    "dot" -> DecimalSeparator.Dot
                    else -> DecimalSeparator.Comma
                },
                thousandSeparator = when (preferences[THOUSAND_SEPARATOR]) {
                    "dot" -> ThousandSeparator.Dot
                    "space" -> ThousandSeparator.Space
                    else -> ThousandSeparator.Comma
                }
            )
        }
    }
}