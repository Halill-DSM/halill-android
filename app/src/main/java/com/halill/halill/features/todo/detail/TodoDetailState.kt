package com.halill.halill.features.todo.detail

import com.halill.halill.base.MviState
import java.time.LocalDateTime

data class TodoDetailState(
    val todoId: Long,
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    val isComplete: Boolean
): MviState {
    companion object {
        fun initial() =
            TodoDetailState(
                todoId = -1L,
                title = "",
                content = "",
                deadline = LocalDateTime.now(),
                isComplete = false
            )
    }
}