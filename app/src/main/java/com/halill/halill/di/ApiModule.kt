package com.halill.halill.di

import com.halill.data.features.auth.remote.AuthApi
import com.halill.data.features.todo.remote.TodoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Singleton
    @Provides
    fun provideTodoApi(
        retrofit: Retrofit
    ): TodoApi = retrofit.create(TodoApi::class.java)
}