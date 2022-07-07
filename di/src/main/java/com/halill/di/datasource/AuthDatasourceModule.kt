package com.halill.di.datasource

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.data.features.auth.datasource.local.LocalUserDataSourceImpl
import com.halill.data.features.auth.datasource.remote.RemoteLoginDataSource
import com.halill.data.features.auth.datasource.remote.RemoteLoginDataSourceImpl
import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSource
import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDatasourceModule {


    @Singleton
    @Binds
    abstract fun provideRemoteRegisterDataSource(
        remoteRegisterDataSourceImpl: RemoteRegisterDataSourceImpl
    ): RemoteRegisterDataSource

    @Singleton
    @Binds
    abstract fun provideLocalUserDataSource(
        localUserDataSourceImpl: LocalUserDataSourceImpl
    ): LocalUserDataSource

    @Singleton
    @Binds
    abstract fun provideRemoteLoginDataSource(
        remoteLoginDataSourceImpl: RemoteLoginDataSourceImpl
    ): RemoteLoginDataSource
}