package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.repository.DoneTodoRepository
import javax.inject.Inject

class DoneTodoRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
): DoneTodoRepository {
    override suspend fun doneTodo(id: Long) {
        localTodoDataSource.doneTodo(id)
    }
}