package com.halill.data.features.todo.datasource.local

import com.halill.domain.features.todolist.entity.TodoModel

class LocalTodoDataSourceImpl: LocalTodoDataSource {
    override suspend fun getTodoList(): List<TodoModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTodoList(todoList: List<TodoModel>) {
        TODO("Not yet implemented")
    }
}