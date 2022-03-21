package com.halill.halill2.features.list

import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.halill2.base.MviEvent

sealed class ListEvent : MviEvent {
    object SwitchTodoOrDone : ListEvent()
    object EmptyList : ListEvent()
    data class ShowList(
        val doneList: List<TodoEntity>,
        val todoList: List<TodoEntity>
    ) : ListEvent()
}