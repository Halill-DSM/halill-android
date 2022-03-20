package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.domain.features.todo.repository.DoneTodoRepository
import javax.inject.Inject

class DoneTodoRepositoryImpl @Inject constructor(
    private val remoteTodoDataSource: RemoteTodoDataSource
) : DoneTodoRepository {

    override suspend fun doneTodo(id: Long) {
        remoteTodoDataSource.doneTodo(id.toInt())
    }
}