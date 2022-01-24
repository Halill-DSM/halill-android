package com.halill.halill.di.todo

import com.halill.domain.features.todo.repository.GetTodoListRepository
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoUseCaseModule {
    @Singleton
    @Provides
    fun provideGetTodoListUseCase(
        getTodoListRepository: GetTodoListRepository
    ): GetTodoListUseCase = GetTodoListUseCase(getTodoListRepository)
}