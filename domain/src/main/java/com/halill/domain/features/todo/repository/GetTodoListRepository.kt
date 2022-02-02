package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.UserTodoListEntity
import kotlinx.coroutines.flow.Flow

interface GetTodoListRepository {
    suspend fun getTodoList(): Flow<UserTodoListEntity>
}