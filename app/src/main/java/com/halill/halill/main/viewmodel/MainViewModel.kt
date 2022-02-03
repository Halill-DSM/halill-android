package com.halill.halill.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.NotLoginException
import com.halill.domain.exception.UnAuthorizedException
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.usecase.CheckLoginUseCase
import com.halill.domain.features.auth.usecase.GetUserInfoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import com.halill.halill.base.EventFlow
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import com.halill.halill.main.model.MainEvent
import com.halill.halill.main.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTodoListUseCase: GetTodoListUseCase,
    private val doneTodoUseCase: DoneTodoUseCase
) : ViewModel() {
    private val _mainState = MutableStateFlow<MainState>(MainState.LoadingState)
    val mainState: StateFlow<MainState> get() = _mainState

    private val _mainEvent = MutableEventFlow<MainEvent>()
    val mainEvent: EventFlow<MainEvent> = _mainEvent.asEventFlow()

    val showingPage = MutableStateFlow(0)

    fun checkLogin() {
        viewModelScope.launch {
            try {
                checkLoginUseCase.execute(Unit)
            } catch (e: NotLoginException) {
                _mainEvent.emit(MainEvent.StartLogin)
            } catch (e: UnAuthorizedException) {
                _mainEvent.emit(MainEvent.StartLogin)
            }
        }

    }

    fun loadUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.execute(Unit).collect {
                loadTodoList(it)
            }
        }
    }

    private fun loadTodoList(user: UserEntity) {
        viewModelScope.launch {
            getTodoListUseCase.execute(Unit).collect { todoList ->
                if (todoList.doneList.isNotEmpty() || todoList.todoList.isNotEmpty()) {
                    _mainState.value =
                        MainState.ShowTodoListState(user, todoList.todoList, todoList.doneList)
                } else {
                    _mainState.value = MainState.EmptyListState(user)
                }
            }
        }
    }

    fun doneTodo(todoId: Long) {
        viewModelScope.launch {
            doneTodoUseCase.execute(todoId)
            loadUserInfo()
        }
    }
}