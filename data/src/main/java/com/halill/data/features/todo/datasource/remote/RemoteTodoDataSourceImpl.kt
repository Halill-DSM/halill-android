package com.halill.data.features.todo.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity
import javax.inject.Inject

class RemoteTodoDataSourceImpl @Inject constructor(
    private val dataBase: FirebaseFirestore
): RemoteTodoDataSource {

    companion object {
        private const val ALL_COUNT_KEY = "all_count"
        private const val ALL_COUNT_TODO_KEY = "all_todo_count"
        private const val ALL_DONE_COUNT_KEY = "all_done_count"
    }
    override suspend fun getTodoList(email: String): List<TodoEntity> =
        TODO()

    override suspend fun fetchAllTimeCount(): AllTimeTodoCountEntity {
        //dataBase.collection(ALL_COUNT_KEY).get()
        TODO()
    }

    override suspend fun saveAllTimeCount(allTimeTodoCountEntity: AllTimeTodoCountEntity) {
        val data = hashMapOf(
            ALL_COUNT_TODO_KEY to allTimeTodoCountEntity.allCount,
            ALL_DONE_COUNT_KEY to allTimeTodoCountEntity.allDoneCount
        )

        dataBase.collection(ALL_COUNT_KEY).add(data)
    }
}