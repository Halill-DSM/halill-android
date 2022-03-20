package com.halill.data.features.todo.datasource.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.halill.data.util.dataBaseQueryTaskToFlow
import com.halill.data.util.dataBaseTaskToFlow
import com.halill.data.util.toLocalDateTime
import com.halill.domain.exception.ReadFireBaseStoreFailException
import com.halill.domain.features.todo.entity.AllTimeTodoCountEntity
import com.halill.domain.features.todo.entity.TodoEntity
import com.halill.domain.features.todo.param.WriteTodoParam
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class RemoteTodoDataSourceImpl @Inject constructor(
    private val dataBase: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RemoteTodoDataSource {

    companion object {
        private const val ALL_COUNT_KEY = "all_count"

        private const val TODO_KEY = "todoList"
        private const val TODO_TITLE = "title"
        private const val TODO_CONTENT = "content"
        private const val TODO_DEADLINE = "deadline"
        private const val TODO_IS_DONE = "is_done"

        private const val ALL_TODO_COUNT_KEY = "all_todo_count"
        private const val ALL_DONE_COUNT_KEY = "all_done_count"
    }

    override suspend fun getTodoList(): List<TodoEntity> {
        val user = auth.currentUser
        return if (user != null) {
            val data = dataBase.collection(user.email!!).document(TODO_KEY).collection("list")
            try {
                data.get().dataBaseQueryTaskToFlow().single().map {
                    val title: String = it.data[TODO_TITLE] as String
                    val content: String = it.data[TODO_CONTENT] as String
                    val deadline: String = it.data[TODO_DEADLINE] as String
                    val isComplete: Boolean = it.data[TODO_IS_DONE] as Boolean

                    TodoEntity(
                        id = it.id.toLong(),
                        title = title,
                        content = content,
                        deadline = deadline.toLocalDateTime(),
                        isCompleted = isComplete
                    )
                }
            } catch (e: ReadFireBaseStoreFailException) {
                emptyList()
            }

        } else {
            emptyList()
        }
    }

    override suspend fun saveTodo(todo: WriteTodoParam) {
        val userEmail = auth.currentUser!!.email!!
        val data = hashMapOf(
            TODO_TITLE to todo.title,
            TODO_CONTENT to todo.content,
            TODO_DEADLINE to todo.deadline.toString(),
            TODO_IS_DONE to todo.isCompleted
        )
        dataBase.collection(userEmail).document("todo").collection("list").add(data)
    }

    override suspend fun fetchAllTimeCount(): AllTimeTodoCountEntity {
        val userEmail = auth.currentUser!!.email!!
        try {
            val result =
                dataBase.collection(userEmail).document(ALL_COUNT_KEY).get().dataBaseTaskToFlow()
                    .single()

            val allCount: Int = (result.data?.get(ALL_TODO_COUNT_KEY) ?: 0) as Int
            val allDoneCount: Int = (result.data?.get(ALL_DONE_COUNT_KEY) ?: 0) as Int

            return AllTimeTodoCountEntity(allCount, allDoneCount)
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