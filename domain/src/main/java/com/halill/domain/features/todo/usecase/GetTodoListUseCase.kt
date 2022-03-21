package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.repository.FetchTodoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val fetchTodoListRepository: FetchTodoListRepository
) : UseCase<Unit, Flow<UserTodoListEntity>>() {

    override suspend fun execute(data: Unit): Flow<UserTodoListEntity> =
        fetchTodoListRepository.fetchTodoList()
}