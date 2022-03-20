package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.entity.CurrentTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.entity.toUserTodoListEntity
import com.halill.domain.features.todo.repository.FetchTodoListRepository
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

class FetchTodoListRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
) : FetchTodoListRepository {

    override suspend fun fetchTodoList(): Flow<UserTodoListEntity> = flow {
        emit(localTodoDataSource.fetchTodoList().toUserTodoListEntity())
    }

    override suspend fun fetchTodoListSize(): CurrentTodoCountEntity {
        val list = localTodoDataSource.fetchTodoList().toUserTodoListEntity()
        return CurrentTodoCountEntity(list.todoList.size, list.doneList.size)
    }

    override suspend fun fetchTodoListWithDate(date: LocalDate): List<TodoEntity> =
        localTodoDataSource.fetchTodoListWithDate(date)
}