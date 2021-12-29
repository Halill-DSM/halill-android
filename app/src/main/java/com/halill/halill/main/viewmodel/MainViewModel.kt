package com.halill.halill.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.todo.usecase.GetUserInfoAndTodoListUseCase
import com.halill.halill.main.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoAndTodoListUseCase: GetUserInfoAndTodoListUseCase
): ViewModel() {
    private val _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState> get() = _mainState

    val showingPage = MutableLiveData(0)

    fun loadUserInfoAndTodoList() {
        viewModelScope.launch {
            try {
                getUserInfoAndTodoListUseCase.execute(Unit).collect { loadData ->
                    _mainState.value = if(showingPage.value == 0)MainState.ShowTodoListState(loadData.user, loadData.todoList)
                    else MainState.ShowDoneListState(loadData.user, loadData.doneList)
                }
            } catch (e: NotLoginException) {
                _mainState.value = MainState.NotLoginState
            }
        }

    }
}