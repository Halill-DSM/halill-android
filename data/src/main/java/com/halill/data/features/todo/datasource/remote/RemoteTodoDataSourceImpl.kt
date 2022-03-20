package com.halill.data.features.todo.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halill.data.util.dataBaseTaskToFlow
import com.halill.domain.exception.ReadFireBaseStoreFailException
import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteTodoDataSourceImpl @Inject constructor(
    private val dataBase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RemoteTodoDataSource {

    companion object {
        private const val ALL_COUNT_KEY = "all_count"
        private const val ALL_TODO_COUNT_KEY = "all_todo_count"
        private const val ALL_DONE_COUNT_KEY = "all_done_count"
    }

    override suspend fun fetchAllTimeCount(): Flow<AllTimeTodoCountEntity> {
        val userEmail = auth.currentUser!!.email!!
        try {
            return dataBase.collection(userEmail).document(ALL_COUNT_KEY).get().dataBaseTaskToFlow()
                .map {
                    val allCount: Int = (it.data?.get(ALL_TODO_COUNT_KEY) ?: 0) as Int
                    val allDoneCount: Int = (it.data?.get(ALL_DONE_COUNT_KEY) ?: 0) as Int
                    AllTimeTodoCountEntity(allCount, allDoneCount)
                }
        } catch (e: ReadFireBaseStoreFailException) {
            throw ReadFireBaseStoreFailException()
        }
    }

    override suspend fun saveAllTimeCount(allTimeTodoCountEntity: AllTimeTodoCountEntity) {
        val userEmail = auth.currentUser!!.email!!
        val data = hashMapOf(
            ALL_TODO_COUNT_KEY to allTimeTodoCountEntity.allCount,
            ALL_DONE_COUNT_KEY to allTimeTodoCountEntity.allDoneCount
        )

        dataBase.collection(userEmail).document(ALL_COUNT_KEY).set(data)
    }
}