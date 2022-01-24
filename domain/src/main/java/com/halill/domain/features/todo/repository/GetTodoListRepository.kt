package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.UserTodoList
import kotlinx.coroutines.flow.Flow

interface GetTodoListRepository {
    suspend fun getTodoList(): Flow<UserTodoList>
}