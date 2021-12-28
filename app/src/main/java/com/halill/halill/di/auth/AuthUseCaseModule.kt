package com.halill.halill.di.auth

import com.halill.domain.features.todolist.repository.GetTodoListRepository
import com.halill.domain.features.todolist.repository.GetUserInfoRepository
import com.halill.domain.features.todolist.usecase.GetUserInfoAndTodoListUseCase
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
    ): GetUserInfoAndTodoListUseCase =
        GetUserInfoAndTodoListUseCase(getUserInfoRepository, getTodoListRepository)
}