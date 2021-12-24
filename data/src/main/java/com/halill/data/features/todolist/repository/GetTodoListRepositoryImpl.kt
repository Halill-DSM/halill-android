package com.halill.data.features.todolist.repository

import com.halill.domain.features.todolist.entity.TodoModel
import com.halill.domain.features.todolist.repository.GetTodoListRepository

class GetTodoListRepositoryImpl: GetTodoListRepository {
    override suspend fun getTodoList(): List<TodoModel> {
        TODO("Not yet implemented")
    }
}