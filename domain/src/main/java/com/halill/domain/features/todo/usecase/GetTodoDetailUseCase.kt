package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.GetTodoDetailRepository
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val getTodoDetailRepository: GetTodoDetailRepository
) : UseCase<Long, TodoEntity>() {
    override suspend fun execute(data: Long): TodoEntity =
        getTodoDetailRepository.getTodoDetail(data)

}