package com.halill.domain.features.todo.entity

import com.halill.domain.features.todo.doneList
import com.halill.domain.features.todo.todoList

data class UserTodoListEntity(val todoList: List<TodoEntity>, val doneList: List<TodoEntity>)

fun List<TodoEntity>.toUserTodoListEntity() =
    UserTodoListEntity(
        this.todoList(),
        this.doneList()
    )