package com.halill.data.features.todo.datasource.remote

import com.halill.domain.features.todolist.entity.TodoModel

interface RemoteTodoDataSource {
    suspend fun getTodoList(email: String): List<TodoModel>
}