package com.halill.data.features.todo.datasource.remote

import com.halill.domain.features.todo.entity.TodoEntity
import javax.inject.Inject

class RemoteTodoDataSourceImpl @Inject constructor(): RemoteTodoDataSource {
    override suspend fun getTodoList(email: String): List<TodoEntity> =
        TODO()
}