package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.param.WriteTodoParam

interface SaveTodoRepository {

    suspend fun saveTodo(todo: WriteTodoParam)
}