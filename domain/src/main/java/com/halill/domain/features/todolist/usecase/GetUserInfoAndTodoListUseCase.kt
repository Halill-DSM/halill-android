package com.halill.domain.features.todolist.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todolist.entity.UserTodoList
import com.halill.domain.features.todolist.repository.GetTodoListRepository
import javax.inject.Inject

class GetUserInfoAndTodoListUseCase @Inject constructor(
    private val getTodoListRepository: GetTodoListRepository
): UseCase<Unit, UserTodoList>() {
    override suspend fun execute(data: Unit): UserTodoList {
        TODO("Not yet implemented")
    }
}