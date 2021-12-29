package com.halill.halill.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.usecase.GetUserInfoUseCase
import com.halill.domain.features.todo.usecase.GetTodoListUseCase
import com.halill.halill.main.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getTodoListUseCase: GetTodoListUseCase
) : ViewModel() {
    private val _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState> get() = _mainState

    val showingPage = MutableLiveData(0)

    fun loadUserInfo() {
        viewModelScope.launch {
            try {
                getUserInfoUseCase.execute(Unit)
            } catch (e: NotLoginException) {
                _mainState.value = MainState.NotLoginState
            }
        }
    }

    fun loadTodoList() {
        viewModelScope.launch {
            try {
                getTodoListUseCase.execute(Unit).collect { loadData ->

                }
            } catch (e: NotLoginException) {
                _mainState.value = MainState.NotLoginState
            }
        }
    }
}