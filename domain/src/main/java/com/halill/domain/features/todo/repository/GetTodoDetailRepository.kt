package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface GetTodoDetailRepository {
    fun getTodoDetail(id: Long): Flow<TodoEntity>
}