package com.halill.halill.di.todo

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.repository.DeleteTodoRepositoryImpl
import com.halill.data.features.todo.repository.DoneTodoRepositoryImpl
import com.halill.data.features.todo.repository.GetTodoListRepositoryImpl
import com.halill.data.features.todo.repository.SaveTodoRepositoryImpl
import com.halill.domain.features.todo.repository.DeleteTodoRepository
import com.halill.domain.features.todo.repository.DoneTodoRepository
import com.halill.domain.features.todo.repository.GetTodoListRepository
import com.halill.domain.features.todo.repository.SaveTodoRepository
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
    ): GetTodoListRepository = GetTodoListRepositoryImpl(localTodoDataSource)

    @Singleton
    @Provides
    fun provideSaveTodoRepository(
        localTodoDataSource: LocalTodoDataSource
    ): SaveTodoRepository = SaveTodoRepositoryImpl(localTodoDataSource)

    @Singleton
    @Provides
    fun provideDoneTodoRepository(
        localTodoDataSource: LocalTodoDataSource
    ): DoneTodoRepository = DoneTodoRepositoryImpl(localTodoDataSource)

    @Singleton
    fun provideDeleteTodoRepository(
        localTodoDataSource: LocalTodoDataSource
    ): DeleteTodoRepository = DeleteTodoRepositoryImpl(localTodoDataSource)
}