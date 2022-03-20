package com.halill.data.features.todo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.param.WriteTodoParam
import java.time.LocalDateTime

@Entity(tableName = "todolist")
data class TodoRoomEntity(
    @PrimaryKey
    var id: Int,
    var title: String,
    var content: String,
    var deadline: LocalDateTime,
    var isCompleted: Boolean
)

fun List<TodoRoomEntity>.toEntity() =
    this.map { it.toEntity() }

fun TodoRoomEntity.toEntity(): TodoEntity =
    TodoEntity(
        id = id.toLong(),
        title = title,
        content = content,
        deadline = deadline,
        isCompleted = isCompleted
    )

fun WriteTodoParam.toDataEntity(id: Int) =
    TodoRoomEntity(
        id = id,
        title = title,
        content = content,
        deadline = deadline,
        isCompleted = isCompleted
    )

fun EditTodoParam.toDataEntity() =
    TodoRoomEntity(
        id = todoId.toInt(),
        title = data.title,
        content = data.content,
        deadline = data.deadline,
        isCompleted = data.isCompleted
    )