package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.GetTodoDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoDetailUseCase @Inject constructor(
    private val getTodoDetailRepository: GetTodoDetailRepository
) : UseCase<Long, Flow<TodoEntity>>() {
    override suspend fun execute(data: Long): Flow<TodoEntity> =
        getTodoDetailRepository.getTodoDetail(data)

}