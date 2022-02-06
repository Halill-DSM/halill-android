package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.param.EditTodoParam

interface EditTodoRepository {
    suspend fun editTodo(param: EditTodoParam)
}