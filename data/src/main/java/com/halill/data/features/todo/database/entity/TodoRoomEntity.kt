package com.halill.data.features.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halill.domain.features.todo.entity.TodoEntity
import java.time.LocalDateTime

@Entity(tableName = "todolist")
data class TodoRoomEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    val isCompleted: Boolean
)

fun List<TodoRoomEntity>.toEntity() =
    this.map { it.toEntity() }

fun TodoRoomEntity.toEntity(): TodoEntity =
    TodoEntity(
        id = id,
        title = title,
        content = content,
        deadline = deadline,
        isCompleted = isCompleted
    )

fun TodoEntity.toDataEntity() =
    TodoRoomEntity(
        id, title, content, deadline, isCompleted
    )