package com.halill.domain.features.todolist.repository

import com.halill.domain.features.todolist.entity.TodoModel

interface GetTodoListRepository {
    suspend fun getTodoList(): List<TodoModel>
}