package com.halill.domain.features.todo

import com.halill.domain.features.todo.entity.TodoEntity

fun List<TodoEntity>.todoList(): List<TodoEntity> =
    this.filter { !it.isCompleted }

fun List<TodoEntity>.doneList(): List<TodoEntity> =
    this.filter { it.isCompleted }