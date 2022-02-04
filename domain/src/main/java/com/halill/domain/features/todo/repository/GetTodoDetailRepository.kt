package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.TodoEntity

interface GetTodoDetailRepository {
    suspend fun getTodoDetail(id: Long): TodoEntity
}