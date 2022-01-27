package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import javax.inject.Inject

class SaveTodoUseCase @Inject constructor(

): UseCase<TodoEntity, Unit>() {
    override suspend fun execute(data: TodoEntity) {
        TODO("Not yet implemented")
    }
}