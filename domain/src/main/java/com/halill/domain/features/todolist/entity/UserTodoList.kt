package com.halill.domain.features.todolist.entity

import com.halill.domain.features.auth.entity.User

data class UserTodoList(val user: User, val todoList: List<TodoModel>, val doneList: List<TodoModel>)