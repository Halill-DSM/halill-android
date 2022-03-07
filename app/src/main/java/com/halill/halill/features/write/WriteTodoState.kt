package com.halill.halill.features.write

import com.halill.halill.base.MviState
import java.time.LocalDateTime


data class WriteTodoState(
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    val isEdit: Boolean,
    val editTodoId: Long,
    val editTodoIsComplete: Boolean,
    val showDateSelectDialog: Boolean,
    val showHourSelectDialog: Boolean
): MviState {
    companion object {
        fun initial() =
            WriteTodoState(
                title = "",
                content = "",
                deadline = LocalDateTime.now(),
                isEdit = false,
                editTodoId = -1L,
                editTodoIsComplete = false,
                showDateSelectDialog = false,
                showHourSelectDialog = false
            )

    }
}