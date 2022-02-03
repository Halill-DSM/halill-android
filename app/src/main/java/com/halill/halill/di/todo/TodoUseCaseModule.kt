package com.halill.halill.di.todo

import com.halill.domain.features.todo.repository.DoneTodoRepository
import com.halill.domain.features.todo.repository.GetTodoListRepository
import com.halill.domain.features.todo.repository.SaveTodoRepository
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import com.halill.domain.features.todo.usecase.SaveTodoUseCase
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

    @Singleton
    @Provides
    fun provideSaveTodoUseCase(
        saveTodoRepository: SaveTodoRepository
    ): SaveTodoUseCase = SaveTodoUseCase(saveTodoRepository)

    @Singleton
    @Provides
    fun provideDoneTodoUseCase(
        doneTodoRepository: DoneTodoRepository
    ): DoneTodoUseCase = DoneTodoUseCase(doneTodoRepository)
}