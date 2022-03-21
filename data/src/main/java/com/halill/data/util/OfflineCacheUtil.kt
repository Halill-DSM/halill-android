package com.halill.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.NullPointerException

class OfflineCacheUtil<T> {
    private lateinit var fetchLocalData: suspend () -> T
    private lateinit var fetchRemoteData: suspend () -> T
    private var isNeedRefresh: suspend (localData: T, remoteData: T) -> Boolean =
        { localData, remoteData -> localData != remoteData }
    private var isNeedRefreshRemoteData: suspend (localData: T, remoteData: T) -> Boolean =
        { localData, remoteData -> localData != remoteData }
    private lateinit var refreshLocalData: suspend (remoteData: T) -> Unit
    private lateinit var refreshRemoteData: suspend (localData: T) -> Unit

    fun localData(fetchLocalData: suspend () -> T): OfflineCacheUtil<T> =
        this.apply { this.fetchLocalData = fetchLocalData }

    fun remoteData(fetchRemoteData: suspend () -> T): OfflineCacheUtil<T> =
        this.apply { this.fetchRemoteData = fetchRemoteData }

    fun compareData(isNeedRefresh: suspend (localData: T, remoteData: T) -> Boolean): OfflineCacheUtil<T> =
        this.apply { this.isNeedRefresh = isNeedRefresh }

    fun isNeedToRefreshRemoteData(isNeedRefresh: suspend (localData: T, remoteData: T) -> Boolean): OfflineCacheUtil<T> =
        this.apply { this.isNeedRefreshRemoteData = isNeedRefresh }

    fun doOnNeedRefresh(refreshLocalData: suspend (remoteData: T) -> Unit): OfflineCacheUtil<T> =
        this.apply { this.refreshLocalData = refreshLocalData }

    fun doRefreshRemote(refreshRemoteData: suspend (localData: T) -> Unit): OfflineCacheUtil<T> =
        this.apply { this.refreshRemoteData = refreshRemoteData }

    fun createFlow(): Flow<T> =
        flow {
            val remoteData = fetchRemoteData()
            try {
                val localData = fetchLocalData()
                emit(localData)

                if (isNeedRefreshRemoteData(localData, remoteData)) {
                    refreshRemoteData(localData)
                }

                if (isNeedRefresh(localData, remoteData)) {
                    refreshLocalData(remoteData)
                    emit(remoteData)
                }
            } catch (e: NullPointerException) {
                refreshLocalData(remoteData)
                emit(remoteData)
            }
        }
}