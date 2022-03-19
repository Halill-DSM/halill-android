package com.halill.halill.main

import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.usecase.FetchUserInfoUseCase
import com.halill.halill.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchUserInfoUseCase: FetchUserInfoUseCase
) : BaseViewModel<MainState, MainEvent>() {

    override val initialState: MainState
        get() = MainState.initial()

    private val _mainViewEffect = MutableEventFlow<MainViewEffect>()
    val mainViewEffect: EventFlow<MainViewEffect> = _mainViewEffect.asEventFlow()

    fun fetchUserInfo() {
        viewModelScope.launch {
            try {
                val user = fetchUserInfoUseCase.execute(Unit)
                setUser(user)
            } catch (e: NotLoginException) {
                _mainViewEffect.emit(MainViewEffect.StartLogin)
            }
        }
    }

    private fun setUser(userEntity: UserEntity) {
        sendEvent(MainEvent.SetUser(userEntity.name, userEntity.email))
    }

    override fun reduceEvent(oldState: MainState, event: MainEvent) {
        when (event) {
            is MainEvent.SetUser -> {
                setState(oldState.copy(
                    userName = event.name,
                    userEmail = event.email,
                    isLoading = false
                ))
            }
        }
    }


}