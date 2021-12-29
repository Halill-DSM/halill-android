package com.halill.halill.di.auth

import com.halill.domain.features.todo.repository.GetTodoListRepository
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {
    @Singleton
    @Provides
    fun provideGetUserInfoAndTodoListUseCase(
        getTodoListRepository: GetTodoListRepository,
        getUserInfoRepository: GetUserInfoRepository
    ): GetTodoListUseCase =
        GetTodoListUseCase(getUserInfoRepository, getTodoListRepository)
}