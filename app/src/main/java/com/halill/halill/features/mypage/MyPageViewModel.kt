package com.halill.halill.features.mypage

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.domain.features.auth.usecase.LogoutUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.base.EventFlow
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<MyPageState, MyPageEvent>() {
    override val initialState: MyPageState
        get() = MyPageState.initial()

    private val _myPageViewEffect = MutableEventFlow<MyPageViewEffect>()
    val myPageViewEffect: EventFlow<MyPageViewEffect> = _myPageViewEffect.asEventFlow()

    suspend fun logout() {
        logoutUseCase.execute(Unit)
        _myPageViewEffect.emit(MyPageViewEffect.StartLogin)
    }

    fun setUser(userEntity: UserEntity) {
        sendEvent(MyPageEvent.SetUser(userEntity))
    }

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {
        when (event) {
            is MyPageEvent.SetAllTimeTodoCount -> {
                setState(
                    oldState.copy(
                        allTimeTodoCount = event.allTimeTodoCount,
                        allTimeDoneTodoCount = event.allTimeDoneCount
                    )
                )
            }

            is MyPageEvent.SetCurrentTodoCount -> {
                setState(
                    oldState.copy(
                        currentTodoCount = event.currentTodoCount,
                        currentDoneCount = event.currentDoneCount
                    )
                )
            }

            is MyPageEvent.SetUser -> {
                setState(
                    oldState.copy(
                        name = event.userEntity.name,
                        email = event.userEntity.email
                    )
                )
            }
        }
    }
}