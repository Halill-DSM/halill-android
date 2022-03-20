package com.halill.data.features.todo.datasource.remote

import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.WriteTodoParam

interface RemoteTodoDataSource {

    suspend fun getTodoList(): List<TodoEntity>

    suspend fun saveTodo(todo: WriteTodoParam)

    suspend fun fetchAllTimeCount(): AllTimeTodoCountEntity

    suspend fun saveAllTimeCount(allTimeTodoCountEntity: AllTimeTodoCountEntity)
}