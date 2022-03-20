package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.CurrentTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.entity.UserTodoListEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface FetchTodoListRepository {

    suspend fun getTodoList(): Flow<UserTodoListEntity>

    suspend fun getTodoListSize(): CurrentTodoCountEntity

    suspend fun getTodoListWithDate(date: LocalDateTime): List<TodoEntity>
}