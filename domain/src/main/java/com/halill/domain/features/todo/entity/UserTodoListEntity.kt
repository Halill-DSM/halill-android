package com.halill.domain.features.todo.entity

data class UserTodoListEntity(val todoList: List<TodoEntity>, val doneList: List<TodoEntity>)

fun List<TodoEntity>.toUserTodoListEntity() =
    UserTodoListEntity(
        this.filter { !it.isCompleted },
        this.filter { it.isCompleted }
    )