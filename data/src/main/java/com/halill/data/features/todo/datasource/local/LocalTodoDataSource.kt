package com.halill.data.features.todo.datasource.local

import com.halill.domain.features.todolist.entity.TodoModel

interface LocalTodoDataSource {
    suspend fun getTodoList(): List<TodoModel>
}