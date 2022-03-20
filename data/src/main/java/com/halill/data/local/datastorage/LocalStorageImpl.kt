package com.halill.data.local.datastorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalStorageImpl @Inject constructor(
    private val context: Context
) : LocalStorage {

    companion object {
        private val IS_LOGIN_KEY = booleanPreferencesKey("is_login")
        private val ALL_TIME_COUNT_KEY = intPreferencesKey("all_time_count_key")
        private val ALL_TIME_DONE_COUNT_KEY = intPreferencesKey("all_time_done_key")
    }

    override suspend fun isLoginState(): Flow<Boolean> =
        context.dataStore.data.map {
            it[IS_LOGIN_KEY] ?: false
        }


    override suspend fun saveIsLoginState() {
        context.dataStore.edit {
            it[IS_LOGIN_KEY] = true
        }
    }

    override suspend fun saveIsNotLoginState() {
        context.dataStore.edit {
            it[IS_LOGIN_KEY] = false
        }
    }

    override suspend fun fetchAllTimeCount(): Flow<Int> =
        context.dataStore.data.map {
            it[ALL_TIME_COUNT_KEY] ?: 0
        }

    override suspend fun plusOneAllTimeCount() {
        context.dataStore.edit {
            val oldValue = it[ALL_TIME_COUNT_KEY] ?: 0
            it[ALL_TIME_COUNT_KEY] = oldValue + 1
        }
    }

    override suspend fun fetchAllTimeDoneCount(): Flow<Int> =
        context.dataStore.data.map {
            it[ALL_TIME_DONE_COUNT_KEY] ?: 0
        }

    override suspend fun plusOneAllTimeDoneCount() {
        context.dataStore.edit {
            val oldValue = it[ALL_TIME_DONE_COUNT_KEY] ?: 0
            it[ALL_TIME_DONE_COUNT_KEY] = oldValue + 1
        }
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "halill")
