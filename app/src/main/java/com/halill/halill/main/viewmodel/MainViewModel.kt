package com.halill.halill.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.NotLoginException
import com.halill.domain.exception.UnAuthorizedException
import com.halill.domain.features.auth.usecase.CheckLoginUseCase
import com.halill.domain.features.auth.usecase.GetUserInfoUseCase
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import com.halill.halill.base.EventFlow
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.Reducer
import com.halill.halill.base.asEventFlow
import com.halill.halill.main.MainUiEvent
import com.halill.halill.main.MainViewEffect
import com.halill.halill.main.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTodoListUseCase: GetTodoListUseCase,
    private val doneTodoUseCase: DoneTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val reducer = MainReducer(MainState.initial())
    val mainState: StateFlow<MainState> get() = reducer.state

    private val _mainViewEffect = MutableEventFlow<MainViewEffect>()
    val mainViewEffect: EventFlow<MainViewEffect> = _mainViewEffect.asEventFlow()

    val showingPage = MutableStateFlow(0)

    fun checkLogin() {
        viewModelScope.launch {
            try {
                checkLoginUseCase.execute(Unit)
            } catch (e: NotLoginException) {
                emitViewEffect(MainViewEffect.StartLogin)
            } catch (e: UnAuthorizedException) {
                emitViewEffect(MainViewEffect.StartLogin)
            } catch (e: Exception) {

            }
        }

    }

    fun loadUserInfo() {
        viewModelScope.launch {
            getUserInfoUseCase.execute(Unit).collect {
                sendEvent(MainUiEvent.ShowUser(it))
                loadTodoList()
            }
        }
    }

    private fun loadTodoList() {
        viewModelScope.launch {
            getTodoListUseCase.execute(Unit).collect { entity ->
                if (checkBothListIsNotEmpty(entity)) {
                    sendEvent(MainUiEvent.ShowList(entity.doneList, entity.todoList))
                } else {
                    sendEvent(MainUiEvent.EmptyList)
                }
            }
        }
    }

    private fun checkBothListIsNotEmpty(entity: UserTodoListEntity): Boolean =
        entity.doneList.isNotEmpty() || entity.todoList.isNotEmpty()

    fun doneTodo(todoId: Long) {
        viewModelScope.launch {
            doneTodoUseCase.execute(todoId)
            loadUserInfo()
        }
    }

    fun deleteTodo(todoId: Long) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(todoId)
            emitViewEffect(MainViewEffect.DoneDeleteTodo)
            loadUserInfo()
        }
    }

    fun startDetailTodo(id: Long) {
        viewModelScope.launch {
            emitViewEffect(MainViewEffect.StartTodoDetail(id))
        }
    }

    private class MainReducer(initial: MainState) : Reducer<MainState, MainUiEvent>(initial) {
        override fun reduce(oldState: MainState, event: MainUiEvent) {
            when (event) {
                is MainUiEvent.EmptyList -> {
                    setState(oldState.copy(todoList = emptyList(), doneList = emptyList()))
                }
                is MainUiEvent.ShowUser -> {
                    setState(oldState.copy(user = event.user))
                }
                is MainUiEvent.ShowList -> {
                    setState(oldState.copy(todoList = event.todoList, doneList = event.doneList))
                }
            }
        }

    }

    private fun sendEvent(event: MainUiEvent) {
        reducer.sendEvent(event)
    }

    private fun emitViewEffect(effect: MainViewEffect) {
        viewModelScope.launch {
            _mainViewEffect.emit(effect)
        }
    }
}