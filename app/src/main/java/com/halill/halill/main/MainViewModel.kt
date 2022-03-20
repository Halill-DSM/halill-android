package com.halill.halill.main

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.usecase.CheckLoginUseCase
import com.halill.halill.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase
) : BaseViewModel<MainState, MainEvent>() {

    override val initialState: MainState
        get() = MainState

    private val _mainViewEffect = MutableEventFlow<MainViewEffect>()
    val mainViewEffect = _mainViewEffect.asEventFlow()

    fun fetchUserInfo() {
        viewModelScope.launch {
            checkLoginUseCase.execute(Unit).collect { isLogin ->
                if(!isLogin) {
                    _mainViewEffect.emit(MainViewEffect.StartLogin)
                }
            }
        }
    }

    override fun reduceEvent(oldState: MainState, event: MainEvent) {
    }


}