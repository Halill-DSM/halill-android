package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.FetchTodoDetailRepository
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val fetchTodoDetailRepository: FetchTodoDetailRepository
) : UseCase<Long, TodoEntity>() {
    override suspend fun execute(data: Long): TodoEntity =
        fetchTodoDetailRepository.fetchTodoDetail(data)

}