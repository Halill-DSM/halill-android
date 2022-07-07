package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.repository.FetchTodoListRepository
import org.threeten.bp.LocalDate
import javax.inject.Inject

class FetchTodoListWithDateUseCase @Inject constructor(
    private val repository: FetchTodoListRepository
) : UseCase<LocalDate, List<TodoEntity>>(){

    override suspend fun execute(data: LocalDate): List<TodoEntity> =
        repository.fetchTodoListWithDate(data)
}