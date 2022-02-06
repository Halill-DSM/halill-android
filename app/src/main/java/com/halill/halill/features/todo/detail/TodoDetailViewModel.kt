package com.halill.halill.features.todo.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val getTodoDetailUseCase: GetTodoDetailUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val doneTodoUseCase: DoneTodoUseCase
) : ViewModel() {
    private val _todoDetailState = MutableStateFlow<TodoDetailState>(TodoDetailState.LoadingState)
    val todoDetailState: StateFlow<TodoDetailState> = _todoDetailState

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
}