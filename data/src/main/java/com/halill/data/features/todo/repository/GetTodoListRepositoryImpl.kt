package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.domain.features.todolist.entity.TodoModel
import com.halill.domain.features.todolist.repository.GetTodoListRepository

class GetTodoListRepositoryImpl(
    private val localTodoDataSource: LocalTodoDataSource,
    private val remoteTodoDataSource: RemoteTodoDataSource
) : GetTodoListRepository {
    override suspend fun getTodoList(): List<TodoModel> {

    }
}