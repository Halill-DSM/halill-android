package com.halill.data.features.todo.datasource.remote

import com.halill.data.features.todo.datasource.dto.response.toEntity
import com.halill.data.features.todo.remote.TodoApi
import com.halill.domain.features.todolist.entity.TodoModel

class RemoteTodoDataSourceImpl(private val todoApi: TodoApi): RemoteTodoDataSource {
    override suspend fun getTodoList(email: String): List<TodoModel> =
        todoApi.getTodoList(email).toEntity()

}