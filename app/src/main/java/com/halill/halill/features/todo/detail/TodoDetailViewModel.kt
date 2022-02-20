package com.halill.halill.features.todo.detail

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoDetailUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.base.Reducer
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
        _todoDetailState.value = TodoDetailState.MainState(todoDetail)
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

    }




}