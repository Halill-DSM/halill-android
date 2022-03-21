package com.halill.data.features.todo.datasource.local

import com.halill.data.features.todo.database.dao.TodoDao
import com.halill.data.features.todo.database.entity.toDataEntity
import com.halill.data.features.todo.database.entity.toEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam
import java.time.LocalDate
import javax.inject.Inject

class LocalTodoDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao
) : LocalTodoDataSource {

    override suspend fun fetchTodoList(): List<TodoEntity> =
        todoDao.fetchTodoList().toEntity()

    override suspend fun saveTodoList(todo: WriteTodoParam) {
        todoDao.saveTodoList(todo.toDataEntity())
    }

    override suspend fun doneTodo(id: Long) {
        todoDao.doneTodo(id)
    }

    override suspend fun deleteTodo(id: Long) {
        todoDao.deleteTodo(id)
    }

    override suspend fun fetchTodoDetail(id: Long): TodoEntity =
        todoDao.fetchTodoDetail(id).toEntity()

    override suspend fun editTodo(param: EditTodoParam) {
        todoDao.saveTodoList(param.toDataEntity())
    }

    override suspend fun fetchTodoListWithDate(date: LocalDate): List<TodoEntity> =
        todoDao.fetchTodoListWithDate(date).toEntity()
}