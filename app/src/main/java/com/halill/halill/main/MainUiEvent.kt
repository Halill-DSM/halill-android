package com.halill.halill.main

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviEvent

sealed class MainUiEvent : MviEvent {
    object EmptyList : MainUiEvent()
    data class ShowUser(val user: UserEntity) : MainUiEvent()
    data class ShowList(
        val doneList: List<TodoEntity>,
        val todoList: List<TodoEntity>
    ) : MainUiEvent()
}