package com.halill.data.local.datastorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalStorageImpl @Inject constructor(
    private val context: Context
) : LocalStorage {

    companion object {
        private val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
    }

    override suspend fun isLoginState(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[IS_LOGIN_KEY] ?: false
        }
    }

    override suspend fun saveIsLoginState() {
        context.dataStore.edit {
            it[IS_LOGIN_KEY] = true
        }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "halill")
