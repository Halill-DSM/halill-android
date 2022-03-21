package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.repository.FetchAllTimeCountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllTimeCountRepositoryImpl @Inject constructor(
    private val remoteTodoDataSource: RemoteTodoDataSource
) : FetchAllTimeCountRepository {

    override suspend fun fetchAllTimeCount(): Flow<AllTimeTodoCountEntity> =
        remoteTodoDataSource.fetchAllTimeCount()
}