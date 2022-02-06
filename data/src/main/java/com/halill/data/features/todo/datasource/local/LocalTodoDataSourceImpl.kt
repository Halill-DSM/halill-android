package com.halill.data.features.todo.datasource.local

import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.toDataEntity
import com.halill.data.features.todo.database.entity.toEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam
import javax.inject.Inject

class LocalTodoDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao
) : LocalTodoDataSource {
    override suspend fun getTodoList(): List<TodoEntity> =
        todoDao.getTodoList().toEntity()

    override suspend fun saveTodoList(todo: WriteTodoParam) {
        todoDao.saveTodoList(todo.toDataEntity())
    }

    override suspend fun doneTodo(id: Long) {
        todoDao.doneTodo(id)
    }

    override suspend fun deleteTodo(id: Long) {
        todoDao.deleteTodo(id)
    }

    override suspend fun getTodoDetail(id: Long): TodoEntity =
        todoDao.getTodoDetail(id).toEntity()

    override suspend fun editTodo(param: EditTodoParam) {
        todoDao.saveTodoList(param.toDataEntity())
    }
}