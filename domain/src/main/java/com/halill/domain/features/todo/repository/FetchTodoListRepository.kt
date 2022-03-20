package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.CurrentTodoCountEntity
import com.halill.domain.features.todo.entity.UserTodoListEntity
import kotlinx.coroutines.flow.Flow

interface FetchTodoListRepository {

    suspend fun getTodoList(): Flow<UserTodoListEntity>

    suspend fun getTodoListSize(): CurrentTodoCountEntity
}