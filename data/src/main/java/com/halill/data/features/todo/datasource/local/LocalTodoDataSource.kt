package com.halill.data.features.todo.datasource.local

import com.halill.domain.features.todo.entity.TodoModel

interface LocalTodoDataSource {
    suspend fun getTodoList(): List<TodoModel>

    suspend fun saveTodoList(todoList: List<TodoModel>)
}