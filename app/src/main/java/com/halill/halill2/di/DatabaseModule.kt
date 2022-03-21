package com.halill.halill2.di

import android.content.Context
import androidx.room.Room
import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.local.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideTodoDataBase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(context, TodoDatabase::class.java, "todo-database").build()

    @Singleton
    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao =
        database.todoDao()
}