package com.halill.halill.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.BadRequestException
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.usecase.GetUserInfoUseCase
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
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTodoListUseCase: GetTodoListUseCase
) : ViewModel() {
    private val _mainState = MutableStateFlow(MainState.EmptyListState)
    val mainState: StateFlow<MainState> get() = _mainState

    private val _mainEvent = MutableEventFlow<MainEvent>()
    val mainEvent: EventFlow<MainEvent> = _mainEvent.asEventFlow()

    val showingPage = MutableStateFlow(0)

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            try {
                getUserInfoUseCase.execute(Unit)
            } catch (e: NotLoginException) {
                _mainState.value = MainState.EmptyListState
                _mainEvent.emit(MainEvent.StartLogin)
            }
        }
    }

    fun loadTodoList() {
        viewModelScope.launch {
            try {
                getTodoListUseCase.execute(Unit).collect { loadData ->

                }
            } catch (e: NotLoginException) {
                _mainState.value = MainState.EmptyListState
            } catch (e: BadRequestException) {

            }
        }
    }
}