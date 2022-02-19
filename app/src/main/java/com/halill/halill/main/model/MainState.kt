package com.halill.halill.main.model

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill.base.MviState

sealed class MainState(
    val user: UserEntity?,
    val todoList: List<TodoEntity>,
    val doneList: List<TodoEntity>
) : MviState {
    object LoadingState : MainState(
        user = null,
        todoList = emptyList(),
        doneList = emptyList()
    )

    data class ShowTodoListState(
        val userEntity: UserEntity,
        val todo: List<TodoEntity>,
        val done: List<TodoEntity>
    ) : MainState(
        user = userEntity,
        todoList = todo,
        doneList = done
    )

    data class EmptyListState(val userEntity: UserEntity) : MainState(
        user = userEntity,
        todoList = emptyList(),
        doneList = emptyList()
    )
}
