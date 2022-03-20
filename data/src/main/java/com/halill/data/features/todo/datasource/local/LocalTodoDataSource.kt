package com.halill.data.features.todo.datasource.local

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam
import java.time.LocalDateTime

interface LocalTodoDataSource {

    suspend fun fetchTodoList(): List<TodoEntity>

    suspend fun saveTodoList(todo: WriteTodoParam)

    suspend fun doneTodo(id: Long)

    suspend fun deleteTodo(id: Long)

    suspend fun fetchTodoDetail(id: Long): TodoEntity

    suspend fun editTodo(param: EditTodoParam)

    suspend fun fetchTodoListWithDate(date: LocalDateTime): List<TodoEntity>
}