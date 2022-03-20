package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.entity.toUserTodoListEntity
import com.halill.domain.features.todo.repository.FetchTodoListRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FetchTodoListRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : FetchTodoListRepository {

    override suspend fun getTodoList(): Flow<UserTodoListEntity> = flow {
        emit(localTodoDataSource.fetchTodoList().toUserTodoListEntity())
    }

}