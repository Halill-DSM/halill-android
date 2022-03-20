package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.entity.toUserTodoListEntity
import com.halill.domain.features.todo.repository.FetchTodoListRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FetchTodoListRepositoryImpl @Inject constructor(
    private val remoteTodoDataSource: RemoteTodoDataSource
) : FetchTodoListRepository {

    override suspend fun getTodoList(): Flow<UserTodoListEntity> =
        remoteTodoDataSource.getTodoList().map { it.toUserTodoListEntity() }
}