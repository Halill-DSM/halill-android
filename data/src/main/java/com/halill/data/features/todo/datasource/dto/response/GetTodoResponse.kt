package com.halill.data.features.todo.datasource.dto.response

import com.google.gson.annotations.SerializedName
import com.halill.domain.features.todolist.entity.TodoModel
import java.time.LocalDateTime

data class GetTodoResponse(
    val id: Long,
    val title: String,
    val content: String,
    val deadline: LocalDateTime,
    @SerializedName("is_completed") val isCompleted: Boolean
)

fun List<GetTodoResponse>.toEntity() =
    this.map { TodoModel(it.id, it.title, it.content, it.deadline, it.isCompleted) }