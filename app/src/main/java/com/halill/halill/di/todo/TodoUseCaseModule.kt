package com.halill.halill.di.todo

import com.halill.domain.features.todo.repository.*
import com.halill.domain.features.todo.usecase.*
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

    @Singleton
    @Provides
    fun provideDeleteTodoUseCase(
        deleteTodoRepository: DeleteTodoRepository
    ): DeleteTodoUseCase = DeleteTodoUseCase(deleteTodoRepository)

    @Singleton
    @Provides
    fun provideGetTodoDetailUseCase(
        detailTodoRepository: GetTodoDetailRepository
    ): GetTodoDetailUseCase = GetTodoDetailUseCase(detailTodoRepository)

    @Singleton
    @Provides
    fun provideEditTodoUseCase(
        editTodoRepository: EditTodoRepository
    ): EditTodoUseCase = EditTodoUseCase(editTodoRepository)
}