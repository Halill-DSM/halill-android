package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.repository.DeleteTodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val deleteTodoRepository: DeleteTodoRepository
): UseCase<Long, Unit>() {
    override suspend fun execute(data: Long) {
        deleteTodoRepository.deleteTodo(data)
    }

}