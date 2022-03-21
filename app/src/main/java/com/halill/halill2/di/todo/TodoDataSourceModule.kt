package com.halill.halill2.di.todo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.local.LocalTodoDataSourceImpl
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSourceImpl
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
    fun provideRemoteTodoDataSource(
        dataBase: FirebaseFirestore,
        auth: FirebaseAuth
    ): RemoteTodoDataSource =
        RemoteTodoDataSourceImpl(dataBase, auth)

    @Singleton
    @Provides
    fun provideLocalTodoDataSource(dao: TodoDao): LocalTodoDataSource =
        LocalTodoDataSourceImpl(dao)
}