package com.halill.data.local.localstorage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.halill.data.features.auth.entity.UserData
import com.halill.data.local.localstorage.LocalStorage
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class LocalStorageImpl @Inject constructor(private val context: Context) : LocalStorage {

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")

        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
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

    override suspend fun saveUser(userData: UserData) {
        context.dataStore.edit { store ->
            store[USER_NAME_KEY] = userData.name
            store[USER_EMAIL_KEY] = userData.email
        }
    }

    override suspend fun getAccessToken(): String? =
        context.dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY]
        }.single()

    override suspend fun getRefreshToken(): String? =
        context.dataStore.data.map { preferences ->
            preferences[REFRESH_TOKEN_KEY]
        }.single()

    override suspend fun getUser(): UserData =
        context.dataStore.data.map { preferences ->
            val name = preferences[USER_NAME_KEY]?:""
            val email = preferences[USER_EMAIL_KEY]?:""
            UserData(name, email)
        }.single()
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "halill")