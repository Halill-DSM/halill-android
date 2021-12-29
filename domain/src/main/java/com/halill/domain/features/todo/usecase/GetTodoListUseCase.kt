package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.doneList
import com.halill.domain.features.todo.entity.UserTodoList
import com.halill.domain.features.todo.repository.GetTodoListRepository
import com.halill.domain.features.todo.repository.GetUserInfoRepository
import com.halill.domain.features.todo.todoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val getTodoListRepository: GetTodoListRepository
): UseCase<Unit, Flow<UserTodoList>>() {
    override suspend fun execute(data: Unit): Flow<UserTodoList> =
        flow {
            getTodoListRepository.getTodoList().collect { todoList ->
                UserTodoList(todoList.todoList(), todoList.doneList())
            }
        }
}