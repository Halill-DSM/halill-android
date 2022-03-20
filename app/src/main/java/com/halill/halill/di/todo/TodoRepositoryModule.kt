package com.halill.halill.di.todo

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.features.todo.repository.*
import com.halill.domain.features.todo.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoRepositoryModule {
    @Singleton
    @Provides
    fun provideGetTodoListRepository(
        localTodoDataSource: LocalTodoDataSource
    ): FetchTodoListRepository =
        FetchTodoListRepositoryImpl(localTodoDataSource)

    @Singleton
    @Provides
    fun provideSaveTodoRepository(
        localTodoDataSource: LocalTodoDataSource,
        remoteTodoDataSource: RemoteTodoDataSource
    ): SaveTodoRepository = SaveTodoRepositoryImpl(localTodoDataSource, remoteTodoDataSource)

    @Singleton
    @Provides
    fun provideDoneTodoRepository(
        localTodoDataSource: LocalTodoDataSource,
        remoteTodoDataSource: RemoteTodoDataSource
    ): DoneTodoRepository = DoneTodoRepositoryImpl(localTodoDataSource, remoteTodoDataSource)

    @Singleton
    @Provides
    fun provideDeleteTodoRepository(
        localTodoDataSource: LocalTodoDataSource
    ): DeleteTodoRepository = DeleteTodoRepositoryImpl(localTodoDataSource)

    @Singleton
    @Provides
    fun provideGetTodoDetailRepository(
        localTodoDataSource: LocalTodoDataSource
    ): FetchTodoDetailRepository = FetchTodoDetailRepositoryImpl(localTodoDataSource)

    @Singleton
    @Provides
    fun provideEditTodoRepository(
        localTodoDataSource: LocalTodoDataSource
    ): EditTodoRepository = EditTodoRepositoryImpl(localTodoDataSource)

    @Singleton
    @Provides
    fun provideFetchAllTimeCountRepository(
        remoteTodoDataSource: RemoteTodoDataSource
    ): FetchAllTimeCountRepository = FetchAllTimeCountRepositoryImpl(remoteTodoDataSource)
}