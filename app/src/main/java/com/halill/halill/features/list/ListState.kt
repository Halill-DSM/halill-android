package com.halill.halill.features.list

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState

data class ListState (
    val isLoading: Boolean,
    val showDoneList: Boolean,
    val todoList: List<TodoEntity>,
    val doneList: List<TodoEntity>
) : MviState {
    companion object {
        fun initial() = ListState(
            isLoading = true,
            showDoneList = false,
            todoList = emptyList(),
            doneList = emptyList()
        )
    }
}