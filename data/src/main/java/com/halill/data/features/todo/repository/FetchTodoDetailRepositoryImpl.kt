package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.FetchTodoDetailRepository
import javax.inject.Inject

class FetchTodoDetailRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : FetchTodoDetailRepository {
    override suspend fun fetchTodoDetail(id: Long): TodoEntity =
        localTodoDataSource.fetchTodoDetail(id)
}