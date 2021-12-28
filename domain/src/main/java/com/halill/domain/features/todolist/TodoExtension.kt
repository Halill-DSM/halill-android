package com.halill.domain.features.todolist

import com.halill.domain.features.todolist.entity.TodoModel

fun List<TodoModel>.todoList(): List<TodoModel> =
    this.filter { !it.isCompleted }

fun List<TodoModel>.doneList(): List<TodoModel> =
    this.filter { it.isCompleted }