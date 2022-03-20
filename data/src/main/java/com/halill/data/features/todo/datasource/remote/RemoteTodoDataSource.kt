package com.halill.data.features.todo.datasource.remote

import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import kotlinx.coroutines.flow.Flow

interface RemoteTodoDataSource {

    suspend fun fetchAllTimeCount(): Flow<AllTimeTodoCountEntity>

    suspend fun plusOneToAllCount()

    suspend fun plusOneToAllDoneCount()
}