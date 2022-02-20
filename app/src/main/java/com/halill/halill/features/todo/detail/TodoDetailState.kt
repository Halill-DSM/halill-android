package com.halill.halill.features.todo.detail

import com.halill.halill.base.MviState
import java.time.LocalDateTime

data class TodoDetailState(
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    val isComplete: Boolean
): MviState {
    companion object {
        fun initial() =
            TodoDetailState(
                title = "",
                content = "",
                deadline = LocalDateTime.now(),
                isComplete = false
            )
    }
}