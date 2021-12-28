package com.halill.halill.main.model

import com.halill.domain.features.auth.entity.User
import com.halill.domain.features.todolist.entity.TodoModel

sealed class MainState {
    object LoadingState : MainState()
    data class ShowTodoListState(val user: User, val data: List<TodoModel>) : MainState()
    data class ShowDoneListState(val user: User, val data: List<TodoModel>) : MainState()
    data class ErrorState(val date: String) : MainState()
    object NotLoginState : MainState()
}
