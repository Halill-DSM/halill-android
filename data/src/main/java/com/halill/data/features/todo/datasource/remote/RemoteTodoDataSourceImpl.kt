package com.halill.data.features.todo.datasource.remote

import com.halill.data.features.todo.dto.response.toEntity
import com.halill.data.features.todo.remote.TodoApi
import com.halill.domain.exception.BadRequestException
import com.halill.domain.features.todo.entity.TodoModel
import retrofit2.HttpException
import javax.inject.Inject

class RemoteTodoDataSourceImpl @Inject constructor(private val todoApi: TodoApi): RemoteTodoDataSource {
    override suspend fun getTodoList(email: String): List<TodoModel> =
        try {
            todoApi.getTodoList(email).toEntity()
        } catch (e: HttpException) {
            throw BadRequestException()
        }


}