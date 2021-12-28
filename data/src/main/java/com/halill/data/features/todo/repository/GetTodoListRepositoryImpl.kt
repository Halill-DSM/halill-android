package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.util.OfflineCacheUtil
import com.halill.domain.features.todolist.entity.TodoModel
import com.halill.domain.features.todolist.repository.GetTodoListRepository
import kotlinx.coroutines.flow.Flow

class GetTodoListRepositoryImpl(
    private val localTodoDataSource: LocalTodoDataSource,
    private val remoteTodoDataSource: RemoteTodoDataSource
) : GetTodoListRepository {
    override suspend fun getTodoList(): Flow<List<TodoModel>> =
        OfflineCacheUtil<List<TodoModel>>()
            .localData { localTodoDataSource.getTodoList() }
            .remoteData { remoteTodoDataSource.getTodoList() }
            .createFlow()

}