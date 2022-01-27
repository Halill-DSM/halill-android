package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.TodoEntity

interface SaveTodoRepository {
    suspend fun saveTodo(todo: TodoEntity)
}