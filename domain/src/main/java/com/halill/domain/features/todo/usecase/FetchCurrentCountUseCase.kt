package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.CurrentTodoCountEntity
import com.halill.domain.features.todo.repository.FetchTodoListRepository
import javax.inject.Inject

class FetchCurrentCountUseCase @Inject constructor(
    private val fetchTodoListRepository: FetchTodoListRepository
) : UseCase<Unit, CurrentTodoCountEntity>() {

    override suspend fun execute(data: Unit): CurrentTodoCountEntity =
        fetchTodoListRepository.fetchTodoListSize()
}