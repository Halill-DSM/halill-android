package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.repository.DoneTodoRepository
import javax.inject.Inject

class DoneTodoUseCase @Inject constructor(
    private val repository: DoneTodoRepository
): UseCase<Long, Unit>() {
    override suspend fun execute(data: Long) {
        repository.doneTodo(data)
    }
}