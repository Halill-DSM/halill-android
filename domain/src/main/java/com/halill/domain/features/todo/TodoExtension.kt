package com.halill.domain.features.todo

import com.halill.domain.features.todo.entity.TodoModel

fun List<TodoModel>.todoList(): List<TodoModel> =
    this.filter { !it.isCompleted }

fun List<TodoModel>.doneList(): List<TodoModel> =
    this.filter { it.isCompleted }