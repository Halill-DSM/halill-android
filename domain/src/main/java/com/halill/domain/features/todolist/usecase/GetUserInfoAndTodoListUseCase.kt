package com.halill.domain.features.todolist.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todolist.entity.UserTodoList
import com.halill.domain.features.todolist.repository.GetTodoListRepository
import com.halill.domain.features.todolist.repository.GetUserInfoRepository
import javax.inject.Inject

class GetUserInfoAndTodoListUseCase @Inject constructor(
    private val getUserInfoRepository: GetUserInfoRepository,
    private val getTodoListRepository: GetTodoListRepository
): UseCase<Unit, UserTodoList>() {
    override suspend fun execute(data: Unit): UserTodoList =
        UserTodoList(getUserInfoRepository.getUserInfo(), getTodoListRepository.getTodoList())
}