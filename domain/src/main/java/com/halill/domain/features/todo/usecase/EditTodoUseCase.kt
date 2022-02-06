package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.param.EditTodoParam
import com.halill.domain.features.todo.repository.EditTodoRepository
import javax.inject.Inject

class EditTodoUseCase @Inject constructor(
    private val editTodoRepository: EditTodoRepository
): UseCase<EditTodoParam, Unit>() {
    override suspend fun execute(data: EditTodoParam) {
        editTodoRepository.editTodo(data)
    }
}