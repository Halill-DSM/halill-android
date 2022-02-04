package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.GetTodoDetailRepository
import javax.inject.Inject

class GetTodoDetailRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : GetTodoDetailRepository {
    override suspend fun getTodoDetail(id: Long): TodoEntity =
        localTodoDataSource.getTodoDetail(id)
}