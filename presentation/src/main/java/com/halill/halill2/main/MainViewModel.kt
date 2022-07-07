package com.halill.halill2.main

import com.halill.domain.features.auth.usecase.CheckLoginUseCase
import com.halill.halill2.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase
) : BaseViewModel<MainState, MainEvent>() {

    override val initialState: MainState
        get() = MainState

    private val _mainViewEffect = MutableEventFlow<MainViewEffect>()
    val mainViewEffect = _mainViewEffect.asEventFlow()

    suspend fun checkLogin() {
        checkLoginUseCase.execute(Unit).collect { isLogin ->
            if (!isLogin) {
                _mainViewEffect.emit(MainViewEffect.StartLogin)
            }
        }
    }

    override fun reduceEvent(oldState: MainState, event: MainEvent) {
    }
}