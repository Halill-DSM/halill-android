package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.UserTodoList
import com.halill.domain.features.todo.repository.GetTodoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val getTodoListRepository: GetTodoListRepository
) : UseCase<Unit, Flow<UserTodoList>>() {

    override suspend fun execute(data: Unit): Flow<UserTodoList> =
        getTodoListRepository.getTodoList()
}