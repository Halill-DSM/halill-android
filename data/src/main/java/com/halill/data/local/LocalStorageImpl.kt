package com.halill.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalStorageImpl(private val context: Context): LocalStorage {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { store ->
            store[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { store ->
            store[REFRESH_TOKEN_KEY] = token
        }
    }

    override fun getAccessToken(): Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }

    override fun getRefreshToken(): Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "halill")