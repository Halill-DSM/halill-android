package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.domain.features.todo.param.WriteTodoParam
import com.halill.domain.features.todo.repository.SaveTodoRepository
import javax.inject.Inject

class SaveTodoRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : SaveTodoRepository {

    override suspend fun saveTodo(todo: WriteTodoParam) {
        localTodoDataSource.saveTodoList(todo)
    }
}