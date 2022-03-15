package com.halill.halill.di.auth

import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSource
import com.halill.data.features.auth.repository.RegisterRepositoryImpl
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
}