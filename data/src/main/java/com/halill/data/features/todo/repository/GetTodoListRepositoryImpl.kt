package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.repository.GetTodoListRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetTodoListRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : GetTodoListRepository {

    override suspend fun getTodoList(): Flow<UserTodoListEntity> = flow {
        localTodoDataSource.getTodoList()
    }

}