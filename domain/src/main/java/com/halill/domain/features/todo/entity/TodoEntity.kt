package com.halill.domain.features.todo.entity

import org.threeten.bp.LocalDateTime


data class TodoEntity(
    val id: Long,
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    val isCompleted: Boolean
)