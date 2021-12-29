package com.halill.data.local.database

import androidx.room.Database
import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1)
abstract class TodoDatabase {
    abstract fun todoDao(): TodoDao
}