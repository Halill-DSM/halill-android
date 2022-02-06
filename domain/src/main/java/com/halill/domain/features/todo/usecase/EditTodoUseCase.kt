package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.param.EditTodoParam
import javax.inject.Inject

class EditTodoUseCase @Inject constructor(

): UseCase<EditTodoParam, Unit>() {
    override suspend fun execute(data: EditTodoParam) {
        TODO("Not yet implemented")
    }
}