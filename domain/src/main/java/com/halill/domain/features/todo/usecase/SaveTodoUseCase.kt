package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.param.WriteTodoParam
import com.halill.domain.features.todo.repository.SaveTodoRepository
import javax.inject.Inject

class SaveTodoUseCase @Inject constructor(
    private val saveTodoRepository: SaveTodoRepository
): UseCase<WriteTodoParam, Unit>() {

    override suspend fun execute(data: WriteTodoParam) {
        saveTodoRepository.saveTodo(data)
    }
}