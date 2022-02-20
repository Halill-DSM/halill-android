package com.halill.halill.main

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviEvent

sealed class MainEvent : MviEvent {
    object EmptyList : MainEvent()
    data class ShowUser(val user: UserEntity) : MainEvent()
    data class ShowList(
        val doneList: List<TodoEntity>,
        val todoList: List<TodoEntity>
    ) : MainEvent()
}