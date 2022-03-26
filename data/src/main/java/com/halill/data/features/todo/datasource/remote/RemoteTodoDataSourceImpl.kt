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
                    val allCount: Int = (it.data?.get(ALL_TODO_COUNT_KEY) as Long).toInt()
                    val allDoneCount: Int = (it.data?.get(ALL_DONE_COUNT_KEY) as Long).toInt()
                    AllTimeTodoCountEntity(allCount, allDoneCount)
                }
        } catch (e: ReadFireBaseStoreFailException) {
            throw ReadFireBaseStoreFailException()
        }
    }

    override suspend fun plusOneToAllCount() {
        val userEmail = auth.currentUser!!.email!!
        getOriginalValueToPlusOne(
            userEmail,
            doOnPlusOne = { originalAllCount, originalAllDoneCount ->
                val data = hashMapOf(
                    ALL_TODO_COUNT_KEY to originalAllCount + 1,
                    ALL_DONE_COUNT_KEY to originalAllDoneCount
                )
                dataBase.collection(userEmail).document(ALL_COUNT_KEY).set(data)
            })
    }

    override suspend fun plusOneToAllDoneCount() {
        val userEmail = auth.currentUser!!.email!!
        getOriginalValueToPlusOne(
            userEmail,
            doOnPlusOne = { originalAllCount, originalAllDoneCount ->
                val data = hashMapOf(
                    ALL_TODO_COUNT_KEY to originalAllCount,
                    ALL_DONE_COUNT_KEY to originalAllDoneCount + 1
                )
                dataBase.collection(userEmail).document(ALL_COUNT_KEY).set(data)
            })
    }

    private fun getOriginalValueToPlusOne(email: String, doOnPlusOne: (Int, Int) -> Unit) {
        dataBase.collection(email).document(ALL_COUNT_KEY).get().addOnSuccessListener {
            val originalAllCount: Int = ((it[ALL_TODO_COUNT_KEY] ?: 0) as Long).toInt()
            val originalAllDoneCount: Int = ((it[ALL_DONE_COUNT_KEY] ?: 0) as Long).toInt()

            doOnPlusOne(originalAllCount, originalAllDoneCount)
        }
    }


}