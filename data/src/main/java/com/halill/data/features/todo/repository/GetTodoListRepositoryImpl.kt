package com.halill.data.features.todo.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.util.OfflineCacheUtil
import com.halill.domain.features.todolist.entity.TodoModel
import com.halill.domain.features.todolist.repository.GetTodoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoListRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource,
    private val localAuthDataSource: LocalAuthDataSource,
    private val remoteTodoDataSource: RemoteTodoDataSource
) : GetTodoListRepository {
    override suspend fun getTodoList(): Flow<List<TodoModel>> {
        val email = localAuthDataSource.getUser().email
        return OfflineCacheUtil<List<TodoModel>>()
            .localData { localTodoDataSource.getTodoList() }
            .remoteData { remoteTodoDataSource.getTodoList(email) }
            .compareData { localData, remoteData -> localData.containsAll(remoteData) }
            .doOnNeedRefresh { localTodoDataSource.saveTodoList(it) }
            .createFlow()
    }

}