package com.halill.data.features.todo.dto.response

import com.google.gson.annotations.SerializedName
import com.halill.domain.features.todo.entity.TodoModel
import java.time.LocalDateTime

data class GetTodoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    @SerializedName("is_completed") val isCompleted: Boolean
)

fun List<GetTodoResponse>.toEntity() =
    this.map { it.toEntity() }

fun GetTodoResponse.toEntity() =
    TodoModel(id, title, content, deadline, isCompleted)