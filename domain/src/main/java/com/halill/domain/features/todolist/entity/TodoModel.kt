package com.halill.domain.features.todolist.entity

import java.time.LocalDateTime

data class TodoModel(val id: Long, val title: String, val content: String, val deadline: LocalDateTime, val isCompleted: Boolean)