package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.repository.DeleteTodoRepository
import javax.inject.Inject

class DeleteTodoRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
): DeleteTodoRepository {
    override suspend fun deleteTodo(todoId: Long) {
        localTodoDataSource
    }
}