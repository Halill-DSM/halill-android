package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.repository.EditTodoRepository
import javax.inject.Inject

class EditTodoRepositoryImpl @Inject constructor(
    private val localTodoDataSource: LocalTodoDataSource
): EditTodoRepository{
    override suspend fun editTodo(param: EditTodoParam) {

    }
}