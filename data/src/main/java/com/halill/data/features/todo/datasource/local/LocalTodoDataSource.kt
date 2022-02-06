package com.halill.data.features.todo.datasource.local

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam

interface LocalTodoDataSource {
    suspend fun getTodoList(): List<TodoEntity>

    suspend fun saveTodoList(todo: WriteTodoParam)

    suspend fun doneTodo(id: Long)

    suspend fun deleteTodo(id: Long)

    suspend fun getTodoDetail(id: Long): TodoEntity

    suspend fun editTodo(param: EditTodoParam)
}