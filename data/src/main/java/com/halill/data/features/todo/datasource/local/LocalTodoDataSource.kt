package com.halill.data.features.todo.datasource.local

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.WriteTodoParam

interface LocalTodoDataSource {
    suspend fun getTodoList(): List<TodoEntity>

    suspend fun saveTodoList(todoList: WriteTodoParam)
}