package com.halill.halill2.di.todo

import com.halill.data.features.todo.repository.*
import com.halill.domain.features.todo.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoRepositoryModule {

    @Singleton
    @Binds
    abstract fun provideGetTodoListRepository(
        fetchTodoListRepositoryImpl: FetchTodoListRepositoryImpl
    ): FetchTodoListRepository

    @Singleton
    @Binds
    abstract fun provideSaveTodoRepository(
        saveTodoRepositoryImpl: SaveTodoRepositoryImpl
    ): SaveTodoRepository

    @Singleton
    @Binds
    abstract fun provideDoneTodoRepository(
        doneTodoRepositoryImpl: DoneTodoRepositoryImpl
    ): DoneTodoRepository

    @Singleton
    @Binds
    abstract fun provideDeleteTodoRepository(
        deleteTodoRepositoryImpl: DeleteTodoRepositoryImpl
    ): DeleteTodoRepository

    @Singleton
    @Binds
    abstract fun provideGetTodoDetailRepository(
        fetchTodoDetailRepositoryImpl: FetchTodoDetailRepositoryImpl
    ): FetchTodoDetailRepository

    @Singleton
    @Binds
    abstract fun provideEditTodoRepository(
        editTodoRepositoryImpl: EditTodoRepositoryImpl
    ): EditTodoRepository

    @Singleton
    @Binds
    abstract fun provideFetchAllTimeCountRepository(
        fetchAllTimeCountRepositoryImpl: FetchAllTimeCountRepositoryImpl
    ): FetchAllTimeCountRepository
}