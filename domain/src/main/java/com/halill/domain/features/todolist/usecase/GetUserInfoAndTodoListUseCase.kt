package com.halill.domain.features.todolist.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todolist.doneList
import com.halill.domain.features.todolist.entity.UserTodoList
import com.halill.domain.features.todolist.repository.GetTodoListRepository
import com.halill.domain.features.todolist.repository.GetUserInfoRepository
import com.halill.domain.features.todolist.todoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserInfoAndTodoListUseCase @Inject constructor(
    private val getUserInfoRepository: GetUserInfoRepository,
    private val getTodoListRepository: GetTodoListRepository
): UseCase<Unit, Flow<UserTodoList>>() {
    override suspend fun execute(data: Unit): Flow<UserTodoList> =
        flow {
            getTodoListRepository.getTodoList().collect { todoList ->
                UserTodoList(getUserInfoRepository.getUserInfo(), todoList.todoList(), todoList.doneList())
            }
        }
}