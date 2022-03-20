package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.TodoEntity
import java.time.LocalDateTime
import javax.inject.Inject

class FetchTodoListWithDateUseCase @Inject constructor(

) : UseCase<LocalDateTime, List<TodoEntity>>(){

    override suspend fun execute(data: LocalDateTime): List<TodoEntity> {
        TODO("Not yet implemented")
    }
}