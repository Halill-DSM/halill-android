package com.halill.halill.features.list

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.todo.entity.UserTodoListEntity
import com.halill.domain.features.todo.usecase.DeleteTodoUseCase
import com.halill.domain.features.todo.usecase.DoneTodoUseCase
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.base.EventFlow
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val doneTodoUseCase: DoneTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : BaseViewModel<ListState, ListEvent>() {

    override val initialState: ListState
        get() = ListState.initial()

    private val _listViewEffect = MutableEventFlow<ListViewEffect>()
    val listViewEffect: EventFlow<ListViewEffect> = _listViewEffect.asEventFlow()

    fun loadTodoList() {
        viewModelScope.launch {
            kotlin.runCatching {
                getTodoListUseCase.execute(Unit).collect { entity ->
                    if (isBothListNotEmpty(entity)) {
                        sendEvent(ListEvent.ShowList(entity.doneList, entity.todoList))
                    } else {
                        sendEvent(ListEvent.EmptyList)
                    }
                }
            }.onFailure {
                sendEvent(ListEvent.EmptyList)
            }

        }
    }

    private fun isBothListNotEmpty(entity: UserTodoListEntity): Boolean =
        entity.doneList.isNotEmpty() || entity.todoList.isNotEmpty()

    fun doneTodo(todoId: String) {
        viewModelScope.launch {
            doneTodoUseCase.execute(todoId)
            loadTodoList()
        }
    }

    fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(todoId)
            emitViewEffect(ListViewEffect.DoneDeleteTodo)
            loadTodoList()
        }
    }

    fun startDetailTodo(id: String) {
        emitViewEffect(ListViewEffect.StartTodoDetail(id))
    }

    private fun emitViewEffect(effect: ListViewEffect) {
        viewModelScope.launch {
            _listViewEffect.emit(effect)
        }
    }

    fun switchTodoOrDone() {
        viewModelScope.launch {
            sendEvent(ListEvent.SwitchTodoOrDone)
        }
    }

    override fun reduceEvent(oldState: ListState, event: ListEvent) {
        when (event) {
            is ListEvent.EmptyList -> {
                setState(
                    oldState.copy(
                        todoList = emptyList(),
                        doneList = emptyList(),
                        isLoading = false
                    )
                )
            }
            is ListEvent.ShowList -> {
                setState(
                    oldState.copy(
                        todoList = event.todoList,
                        doneList = event.doneList,
                        isLoading = false
                    )
                )
            }
            is ListEvent.SwitchTodoOrDone -> {
                setState(
                    oldState.copy(
                        showDoneList = !oldState.showDoneList
                    )
                )
            }
        }
    }
}