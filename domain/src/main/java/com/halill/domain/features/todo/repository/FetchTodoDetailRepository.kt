package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.TodoEntity

interface FetchTodoDetailRepository {
    suspend fun fetchTodoDetail(id: Long): TodoEntity
}