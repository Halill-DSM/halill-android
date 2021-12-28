package com.halill.halill.main.viewmodel

import androidx.lifecycle.ViewModel
import com.halill.domain.features.todolist.usecase.GetUserInfoAndTodoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserInfoAndTodoListUseCase: GetUserInfoAndTodoListUseCase
): ViewModel() {

}