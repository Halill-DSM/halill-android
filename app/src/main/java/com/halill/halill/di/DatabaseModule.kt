package com.halill.halill.di

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
    fun provideTodoDao(@ApplicationContext context: Context): TodoDao =
        Room.databaseBuilder(context, TodoDatabase::class.java, "todo-database").build().todoDao()
}