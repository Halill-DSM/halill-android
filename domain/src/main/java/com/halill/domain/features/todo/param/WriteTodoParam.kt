package com.halill.domain.features.todo.param

import java.time.LocalDateTime

data class WriteTodoParam(
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    val isCompleted: Boolean
)
