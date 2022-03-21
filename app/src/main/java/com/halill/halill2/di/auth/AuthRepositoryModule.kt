package com.halill.halill2.di.auth

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.data.features.auth.datasource.remote.RemoteLoginDataSource
import com.halill.data.features.auth.datasource.remote.RemoteRegisterDataSource
import com.halill.data.features.auth.repository.*
import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.local.datastorage.LocalStorage
import com.halill.domain.features.auth.repository.*
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

    @Singleton
    @Provides
    fun provideLoginRepository(
        remoteLoginDataSource: RemoteLoginDataSource,
        localStorage: LocalStorage
    ): LoginRepository = LoginRepositoryImpl(remoteLoginDataSource, localStorage)

    @Singleton
    @Provides
    fun provideLogoutRepository(
        localUserDataSource: LocalUserDataSource,
        localTodoDataSource: LocalTodoDataSource
    ): LogoutRepository = LogoutRepositoryImpl(localUserDataSource, localTodoDataSource)

    @Singleton
    @Provides
    fun provideSaveUserNameRepository(
        localUserDataSource: LocalUserDataSource
    ): SaveUserNameRepository = SaveUserNameRepositoryImpl(localUserDataSource)
}