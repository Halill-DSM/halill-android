package com.halill.halill.di.auth

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.local.LocalAuthDataSourceImpl
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSourceImpl
import com.halill.data.features.auth.remote.AuthApi
import com.halill.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataSourceModule {
    @Singleton
    @Provides
    fun provideAuthLocalDataSource(localStorage: LocalStorage): LocalAuthDataSource = LocalAuthDataSourceImpl(localStorage)

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(api: AuthApi): RemoteAuthDataSource = RemoteAuthDataSourceImpl(api)
}