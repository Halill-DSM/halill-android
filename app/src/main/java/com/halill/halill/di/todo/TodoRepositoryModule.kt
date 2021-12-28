package com.halill.halill.di.todo

import com.halill.data.features.todo.repository.GetTodoListRepositoryImpl
import com.halill.domain.features.todolist.repository.GetTodoListRepository
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
    fun provideGetTodoListRepository(): GetTodoListRepository = GetTodoListRepositoryImpl()
}