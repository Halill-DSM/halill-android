package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.CurrentTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.entity.UserTodoListEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface FetchTodoListRepository {

    suspend fun fetchTodoList(): Flow<UserTodoListEntity>

    suspend fun fetchTodoListSize(): CurrentTodoCountEntity

    suspend fun fetchTodoListWithDate(date: LocalDate): List<TodoEntity>
}