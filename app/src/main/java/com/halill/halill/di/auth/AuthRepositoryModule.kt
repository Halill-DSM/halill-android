package com.halill.halill.di.auth

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.data.features.auth.repository.LoginRepositoryImpl
import com.halill.data.features.auth.repository.RegisterRepositoryImpl
import com.halill.data.features.todo.repository.GetUserInfoRepositoryImpl
import com.halill.domain.features.auth.repository.LoginRepository
import com.halill.domain.features.auth.repository.RegisterRepository
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {
    @Singleton
    @Provides
    fun provideGetUserInfoRepository(
        localAuthDataSource: LocalAuthDataSource,
        remoteAuthDataSource: RemoteAuthDataSource
    ): GetUserInfoRepository = GetUserInfoRepositoryImpl(remoteAuthDataSource, localAuthDataSource)

    @Singleton
    @Provides
    fun provideLoginRepository(
        localAuthDataSource: LocalAuthDataSource,
        remoteAuthDataSource: RemoteAuthDataSource
    ): LoginRepository = LoginRepositoryImpl(remoteAuthDataSource, localAuthDataSource)

    @Singleton
    @Provides
    fun provideRegisterRepository(
        remoteAuthDataSource: RemoteAuthDataSource
    ): RegisterRepository = RegisterRepositoryImpl(remoteAuthDataSource)
}