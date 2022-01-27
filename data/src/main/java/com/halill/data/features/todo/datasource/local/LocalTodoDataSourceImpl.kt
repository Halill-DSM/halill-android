package com.halill.data.features.todo.datasource.local

import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.toDataEntity
import com.halill.data.features.todo.database.entity.toEntity
import com.halill.domain.features.todo.entity.TodoEntity
import javax.inject.Inject

class LocalTodoDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao
): LocalTodoDataSource {
    override suspend fun getTodoList(): List<TodoEntity> =
        todoDao.getTodoList().toEntity()

    override suspend fun saveTodoList(todoList: TodoEntity) {
        todoDao.saveTodoList(todoList.toDataEntity())
    }
}