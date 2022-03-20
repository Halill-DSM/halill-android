package com.halill.domain.features.todo.repository

import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import kotlinx.coroutines.flow.Flow

interface FetchAllTimeCountRepository {

    suspend fun fetchAllTimeCount(): Flow<AllTimeTodoCountEntity>
}