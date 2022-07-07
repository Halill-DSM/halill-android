package com.halill.di.repository

import com.halill.data.features.auth.repository.*
import com.halill.domain.features.auth.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRegisterRepository(
        registerRepositoryImpl: RegisterRepositoryImpl
    ): RegisterRepository

    @Singleton
    @Binds
    abstract fun provideUserRepository(
        fetchUserInfoRepositoryImpl: FetchUserInfoRepositoryImpl
    ): FetchUserInfoRepository

    @Singleton
    @Binds
    abstract fun provideLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Singleton
    @Binds
    abstract fun provideLogoutRepository(
        logoutRepositoryImpl: LogoutRepositoryImpl
    ): LogoutRepository

    @Singleton
    @Binds
    abstract fun provideSaveUserNameRepository(
        saveUserNameRepositoryImpl: SaveUserNameRepositoryImpl
    ): SaveUserNameRepository
}