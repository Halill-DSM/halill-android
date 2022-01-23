package com.halill.data.features.todo.repository

import com.halill.data.features.auth.datasource.local.LocalAuthDataSource
import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.util.OfflineCacheUtil
import com.halill.domain.features.todo.doneList
import com.halill.domain.features.todo.entity.TodoModel
import com.halill.domain.features.todo.entity.UserTodoList
import com.halill.domain.features.todo.repository.GetTodoListRepository
import com.halill.domain.features.todo.todoList
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetTodoListRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource,
    private val localAuthDataSource: LocalAuthDataSource,
    private val remoteTodoDataSource: RemoteTodoDataSource
) : GetTodoListRepository {
    override suspend fun getTodoList(): Flow<UserTodoList> {
        val email = localAuthDataSource.getUser().singleOrNull()?.email?:""

        return OfflineCacheUtil<List<TodoModel>>()
            .localData { localTodoDataSource.getTodoList() }
            .remoteData { remoteTodoDataSource.getTodoList(email) }
            .compareData { localData, remoteData -> localData.containsAll(remoteData) }
            .doOnNeedRefresh { localTodoDataSource.saveTodoList(it) }
            .createFlow()
            .map { UserTodoList(it.todoList(), it.doneList()) }

    }

}