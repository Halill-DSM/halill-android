package com.halill.halill.main

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import com.halill.halill.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val doneTodoUseCase: DoneTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : BaseViewModel<MainState, MainEvent>() {

    override val initialState: MainState
        get() = MainState.initial()

    private val _mainViewEffect = MutableEventFlow<MainViewEffect>()
    val mainViewEffect: EventFlow<MainViewEffect> = _mainViewEffect.asEventFlow()

    fun loadTodoList() {
        viewModelScope.launch {
            getTodoListUseCase.execute(Unit).collect { entity ->
                if (checkBothListIsNotEmpty(entity)) {
                    sendEvent(MainEvent.ShowList(entity.doneList, entity.todoList))
                } else {
                    sendEvent(MainEvent.EmptyList)
                }
            }
        }
    }

    private fun checkBothListIsNotEmpty(entity: UserTodoListEntity): Boolean =
        entity.doneList.isNotEmpty() || entity.todoList.isNotEmpty()

    fun doneTodo(todoId: Long) {
        viewModelScope.launch {
            doneTodoUseCase.execute(todoId)
            loadTodoList()
        }
    }

    fun deleteTodo(todoId: Long) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(todoId)
            emitViewEffect(MainViewEffect.DoneDeleteTodo)
            loadTodoList()
        }
    }

    fun startDetailTodo(id: Long) {
        emitViewEffect(MainViewEffect.StartTodoDetail(id))
    }

    private fun emitViewEffect(effect: MainViewEffect) {
        viewModelScope.launch {
            _mainViewEffect.emit(effect)
        }
    }

    override fun reduceEvent(oldState: MainState, event: MainEvent) {
        when (event) {
            is MainEvent.EmptyList -> {
                setState(
                    oldState.copy(
                        todoList = emptyList(),
                        doneList = emptyList(),
                        isLoading = false
                    )
                )
            }
            is MainEvent.ShowUser -> {
                setState(oldState.copy(user = event.user, isLoading = false))
            }
            is MainEvent.ShowList -> {
                setState(
                    oldState.copy(
                        todoList = event.todoList,
                        doneList = event.doneList,
                        isLoading = false
                    )
                )
            }
        }
    }


}