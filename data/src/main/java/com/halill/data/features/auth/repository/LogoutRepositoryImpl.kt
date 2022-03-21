package com.halill.data.features.auth.repository

import com.halill.data.features.auth.datasource.local.LocalUserDataSource
import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.domain.features.auth.repository.LogoutRepository
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val localUserDataSource: LocalUserDataSource,
    private val localTodoDataSource: LocalTodoDataSource
) : LogoutRepository {
    
    override suspend fun logout() {
        localUserDataSource.deleteUser()
        localTodoDataSource.deleteAllTodo()
    }
}