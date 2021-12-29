package com.halill.domain.features.todolist.repository

import com.halill.domain.features.todolist.entity.TodoModel
import kotlinx.coroutines.flow.Flow

interface GetTodoListRepository {
    suspend fun getTodoList(): Flow<List<TodoModel>>
}