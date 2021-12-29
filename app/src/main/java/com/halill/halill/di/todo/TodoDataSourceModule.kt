package com.halill.halill.di.todo

import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.local.LocalTodoDataSourceImpl
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSourceImpl
import com.halill.data.features.todo.remote.TodoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoDataSourceModule {
    @Singleton
    @Provides
    fun provideRemoteTodoDataSource(api: TodoApi): RemoteTodoDataSource = RemoteTodoDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideLocalTodoDataSource(dao: TodoDao): LocalTodoDataSource = LocalTodoDataSourceImpl(dao)
}