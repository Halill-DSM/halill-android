package com.halill.data.features.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "todolist")
data class TodoEntity(@PrimaryKey val id: Long, val title: String, val content: String, val deadLine: LocalDateTime, val isCompleted: Boolean)