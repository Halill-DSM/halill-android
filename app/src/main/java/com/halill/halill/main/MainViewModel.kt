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
) : BaseViewModel<MainState, MainEvent>() {

    override val initialState: MainState
        get() = MainState.initial()

    private val _mainViewEffect = MutableEventFlow<MainViewEffect>()
    val mainViewEffect: EventFlow<MainViewEffect> = _mainViewEffect.asEventFlow()

    override fun reduceEvent(oldState: MainState, event: MainEvent) {
        when (event) {

        }
    }


}