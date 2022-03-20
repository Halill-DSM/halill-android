package com.halill.domain.features.todo.usecase

import com.halill.domain.base.UseCase
import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.repository.FetchAllTimeCountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllTimeTodoUseCase @Inject constructor(
    private val fetchAllTimeCountRepository: FetchAllTimeCountRepository
) : UseCase<Unit, Flow<AllTimeTodoCountEntity>>() {

    override suspend fun execute(data: Unit): Flow<AllTimeTodoCountEntity> =
        fetchAllTimeCountRepository.fetchAllTimeCount()

}