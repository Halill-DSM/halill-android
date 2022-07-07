package com.halill.halill2.features.detail

import com.halill.halill2.base.MviState
import org.threeten.bp.LocalDateTime

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