package com.halill.halill.di.auth

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.auth.datasource.remote.RemoteAuthDataSource
import com.halill.data.features.todo.repository.GetUserInfoRepositoryImpl
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
}