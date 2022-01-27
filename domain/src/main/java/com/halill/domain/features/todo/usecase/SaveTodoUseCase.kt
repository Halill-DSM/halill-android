package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.SaveTodoRepository
import javax.inject.Inject

class SaveTodoUseCase @Inject constructor(
    private val saveTodoRepository: SaveTodoRepository
): UseCase<TodoEntity, Unit>() {
    override suspend fun execute(data: TodoEntity) {
        saveTodoRepository.saveTodo(data)
    }
}