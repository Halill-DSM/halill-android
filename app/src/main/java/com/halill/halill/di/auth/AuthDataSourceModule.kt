package com.halill.halill.di.auth

import com.google.firebase.auth.FirebaseAuth
import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.data.features.auth.datasource.local.LocalUserDataSourceImpl
import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSource
import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSourceImpl
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
    fun provideRemoteRegisterDataSource(
        auth: FirebaseAuth
    ): RemoteRegisterDataSource = RemoteRegisterDataSourceImpl(auth)

    @Singleton
    @Provides
    fun provideLocalUserDataSource(
        auth: FirebaseAuth
    ): LocalUserDataSource = LocalUserDataSourceImpl(auth)
}