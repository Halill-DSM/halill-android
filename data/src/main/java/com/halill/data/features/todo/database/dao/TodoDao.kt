package com.halill.data.features.todo.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.halill.data.features.todo.database.entity.TodoRoomEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todolist")
    suspend fun getTodoList(): List<TodoRoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodoList(todoRoomList: TodoRoomEntity)

    @Query("UPDATE todoList SET isCompleted = :state where id = :todoId")
    suspend fun doneTodo(todoId: Long, state: Boolean = true)

    @Query("DELETE FROM todoList WHERE id = :todoId")
    suspend fun deleteTodo(todoId: Long)

    @Query("SELECT * FROM todolist WHERE id = :todoId")
    suspend fun getTodoDetail(todoId: Long): TodoRoomEntity
}