package com.halill.domain.features.todo.repository

interface DeleteTodoRepository {
    suspend fun deleteTodo(todoId: Long)
}