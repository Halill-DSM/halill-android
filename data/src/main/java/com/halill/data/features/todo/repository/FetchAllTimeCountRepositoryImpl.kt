package com.halill.data.features.todo.repository

import com.halill.data.features.todo.datasource.local.LocalTodoDataSource
import com.halill.data.features.todo.datasource.remote.RemoteTodoDataSource
import com.halill.data.util.OfflineCacheUtil
import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.repository.FetchAllTimeCountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchAllTimeCountRepositoryImpl @Inject constructor(
    private val remoteTodoDataSource: RemoteTodoDataSource,
    private val localTodoDataSource: LocalTodoDataSource
) : FetchAllTimeCountRepository {

    override suspend fun fetchAllTimeCount(): Flow<AllTimeTodoCountEntity> =
        OfflineCacheUtil<AllTimeTodoCountEntity>()
            .localData { localTodoDataSource.fetchAllTimeCount() }
            .remoteData { remoteTodoDataSource.fetchAllTimeCount() }
            .isNeedToRefreshRemoteData { localData, remoteData ->
                (localData.allCount < remoteData.allCount) || (localData.allDoneCount < remoteData.allDoneCount)
            }
            .doRefreshRemote { localData -> remoteTodoDataSource.saveAllTimeCount(localData) }
            .createFlow()
}