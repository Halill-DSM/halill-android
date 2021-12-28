package com.halill.halill.di

import com.halill.data.features.auth.remote.AuthApi
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val TODO_BASE_URL = "/todo"
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)
}