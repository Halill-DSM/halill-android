package com.halill.data.features.todo.datasource.local

import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.toDataEntity
import com.halill.data.features.todo.database.entity.toEntity
import com.halill.domain.features.todolist.entity.TodoModel
import javax.inject.Inject

class LocalTodoDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao
): LocalTodoDataSource {
    override suspend fun getTodoList(): List<TodoModel> =
        todoDao.getTodoList().toEntity()

    override suspend fun saveTodoList(todoList: List<TodoModel>) {
        todoDao.saveTodoList(todoList.toDataEntity())
    }
}