package com.halill.di.datasource

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.local.LocalTodoDataSourceImpl
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoDatasourceModule {

    @Singleton
    @Binds
    abstract fun provideRemoteTodoDataSource(
        remoteTodoDataSourceImpl: RemoteTodoDataSourceImpl
    ): RemoteTodoDataSource

    @Singleton
    @Binds
    abstract fun provideLocalTodoDataSource(
        localTodoDataSourceImpl: LocalTodoDataSourceImpl
    ): LocalTodoDataSource
}