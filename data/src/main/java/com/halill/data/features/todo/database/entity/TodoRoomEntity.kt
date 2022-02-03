package com.halill.data.features.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.WriteTodoParam
import java.time.LocalDateTime

@Entity(tableName = "todolist")
data class TodoRoomEntity(
    var title: String,
    var content: String,
    var deadline: LocalDateTime,
    var isCompleted: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

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

fun WriteTodoParam.toDataEntity() =
    TodoRoomEntity(
        title = title,
        content = content,
        deadline = deadline,
        isCompleted = isCompleted
    )