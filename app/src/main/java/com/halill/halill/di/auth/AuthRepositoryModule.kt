package com.halill.halill.di.auth

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.data.features.auth.datasource.local.LocalUserDataSourceImpl
import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSource
import com.halill.data.features.auth.repository.FetchUserInfoRepositoryImpl
import com.halill.data.features.auth.repository.RegisterRepositoryImpl
import com.halill.domain.features.auth.repository.FetchUserInfoRepository
import com.halill.domain.features.auth.repository.RegisterRepository
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
    fun provideRegisterRepository(
        remoteRegisterDataSource: RemoteRegisterDataSource
    ): RegisterRepository = RegisterRepositoryImpl(remoteRegisterDataSource)

    @Singleton
    @Provides
    fun provideUserRepository(
        localUserDataSource: LocalUserDataSource
    ): FetchUserInfoRepository = FetchUserInfoRepositoryImpl(localUserDataSource)
}