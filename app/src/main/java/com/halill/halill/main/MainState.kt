package com.halill.halill.main

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState

data class MainState(
    val user: UserEntity,
    val isLoading: Boolean,
    val todoList: List<TodoEntity>,
    val doneList: List<TodoEntity>
) : MviState {
    companion object {
        fun initial() = MainState(
            user = UserEntity("",""),
            isLoading = true,
            todoList = emptyList(),
            doneList = emptyList()
        )
    }
}
