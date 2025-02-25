package com.myapp.spendless.util

import android.content.Context
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID


val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val TOTAL_AMOUNT = doublePreferencesKey("total_amount")
    }

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

    suspend fun clearUserId() {
        context.dataStore.edit { it.clear() }
    }
}