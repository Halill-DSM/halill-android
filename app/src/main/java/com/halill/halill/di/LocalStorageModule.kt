package com.halill.halill.di

import android.content.Context
import com.halill.data.local.localstorage.LocalStorage
import com.halill.data.local.localstorage.LocalStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {
    @Singleton
    @Provides
    fun provideLocalStorage(@ApplicationContext context: Context): LocalStorage = LocalStorageImpl(context)
}