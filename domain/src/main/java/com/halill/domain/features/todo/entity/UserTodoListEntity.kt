package com.halill.domain.features.todo.entity

data class UserTodoListEntity(val todoList: List<TodoEntity>, val doneList: List<TodoEntity>)