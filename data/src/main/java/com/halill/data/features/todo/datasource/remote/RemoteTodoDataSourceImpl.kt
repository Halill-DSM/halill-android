package com.halill.data.features.todo.datasource.remote

import com.halill.data.features.todo.dto.response.toEntity
import com.halill.data.features.todo.remote.TodoApi
import com.halill.domain.features.todo.entity.TodoModel
import javax.inject.Inject

class RemoteTodoDataSourceImpl @Inject constructor(private val todoApi: TodoApi): RemoteTodoDataSource {
    override suspend fun getTodoList(email: String): List<TodoModel> =
        todoApi.getTodoList(email).toEntity()

}