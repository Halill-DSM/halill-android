package com.halill.domain.features.todo.repository

interface DoneTodoRepository {
    suspend fun doneTodo(id: Long)
}