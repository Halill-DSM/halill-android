package com.halill.data.features.todo.datasource.remote

import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity

interface RemoteTodoDataSource {

    suspend fun getTodoList(email: String): List<TodoEntity>

    suspend fun fetchAllTimeCount(): AllTimeTodoCountEntity

    suspend fun saveAllTimeCount(allTimeTodoCountEntity: AllTimeTodoCountEntity)
}