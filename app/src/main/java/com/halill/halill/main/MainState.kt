package com.halill.halill.main

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState

data class MainState(
    val isLoading: Boolean,
    val showDoneList: Boolean,
    val todoList: List<TodoEntity>,
    val doneList: List<TodoEntity>
) : MviState {
    companion object {
        fun initial() = MainState(
            isLoading = true,
            showDoneList = false,
            todoList = emptyList(),
            doneList = emptyList()
        )
    }
}
