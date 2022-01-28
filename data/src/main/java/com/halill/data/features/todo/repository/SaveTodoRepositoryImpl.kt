package com.halill.data.features.todo.repository

import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.toDataEntity
import com.halill.domain.features.todo.param.WriteTodoParam
import com.halill.domain.features.todo.repository.SaveTodoRepository
import javax.inject.Inject

class SaveTodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
): SaveTodoRepository {
    override suspend fun saveTodo(todo: WriteTodoParam) {
        todoDao.saveTodoList(todo.toDataEntity())
    }
}