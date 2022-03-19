package com.halill.halill.main

import androidx.lifecycle.viewModelScope
import com.halill.domain.exception.NotLoginException
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.usecase.FetchUserInfoUseCase
import com.halill.halill.base.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchUserInfoUseCase: FetchUserInfoUseCase
) : BaseViewModel<MainState, MainEvent>() {

    override val initialState: MainState
        get() = MainState.initial()

    suspend fun fetchUserInfo() {
        try {
            val user = fetchUserInfoUseCase.execute(Unit)
            user.collect {
                setUser(it)
            }
            sendEvent(MainEvent.DoneLogin)
        } catch (e: NotLoginException) {
            sendEvent(MainEvent.NeedLogin)
        }
    }

    private fun setUser(userEntity: UserEntity) {
        sendEvent(MainEvent.SetUser(userEntity.name, userEntity.email))
    }

    override fun reduceEvent(oldState: MainState, event: MainEvent) {
        when (event) {
            is MainEvent.SetUser -> {
                setState(
                    oldState.copy(
                        userEntity = UserEntity(event.name, event.email),
                        isLoading = false
                    )
                )
            }
            is MainEvent.DoneLogin -> {
                setState(
                    oldState.copy(
                        needLogin = false
                    )
                )
            }
            is MainEvent.NeedLogin -> {
                setState(
                    oldState.copy(
                        needLogin = true
                    )
                )
            }
        }
    }


}