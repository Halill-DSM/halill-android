package com.halill.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class LocalStorageImpl(private val context: Context): LocalStorage {

    override fun saveToken(token: String) {
        TODO("Not yet implemented")
    }

    override fun getAccessToken(): String {
        TODO("Not yet implemented")
    }

    override fun getRefreshToken(): String {
        TODO("Not yet implemented")
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "da")