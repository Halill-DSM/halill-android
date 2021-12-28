package com.halill.data.features.todo.datasource.remote

import com.halill.data.features.auth.remote.AuthApi
import com.halill.domain.features.todolist.entity.TodoModel

class RemoteTodoDataSourceImpl(private val authApi: AuthApi): RemoteTodoDataSource {
    override suspend fun getTodoList(): List<TodoModel> {
        TODO("Not yet implemented")
    }
}