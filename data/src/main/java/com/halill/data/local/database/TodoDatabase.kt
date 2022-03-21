package com.halill.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.TodoRoomEntity
import com.halill.data.local.database.converter.LocalDateTimeConverter

@Database(entities = [TodoRoomEntity::class], version = 3)
@TypeConverters(LocalDateTimeConverter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}