package com.halill.data.features.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halill.data.features.todo.database.entity.TodoEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todolist")
    suspend fun getTodoList(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodoList(todoList: List<TodoEntity>)
}