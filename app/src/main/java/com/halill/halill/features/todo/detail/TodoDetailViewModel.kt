package com.halill.halill.features.todo.detail

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoDetailUseCase
import com.halill.halill.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getTodoDetailUseCase: GetTodoDetailUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val doneTodoUseCase: DoneTodoUseCase
) : BaseViewModel<TodoDetailState, TodoDetailEvent>() {

    override val initialState: TodoDetailState
        get() = TodoDetailState.initial()

    suspend fun getDetail(id: Long) {
        val todoDetail = getTodoDetailUseCase.execute(id)
        sendEvent(TodoDetailEvent.ShowTodoDetail(todoDetail))
    }

    fun deleteTodo(id: Long) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(id)
        }
    }

    fun doneTodo(id: Long) {
        viewModelScope.launch {
            doneTodoUseCase.execute(id)
            getDetail(id)
        }
    }

    override fun reduceEvent(oldState: TodoDetailState, event: TodoDetailEvent) {
        when (event) {
            is TodoDetailEvent.ShowTodoDetail -> {
                val todoDetail = event.todo
                setState(
                    oldState.copy(
                        title = todoDetail.title,
                        content = todoDetail.content,
                        deadline = todoDetail.deadline,
                        isComplete = todoDetail.isCompleted
                    )
                )
            }
        }
    }


}