package com.halill.halill.features.mypage

import androidx.lifecycle.viewModelScope
import com.halill.domain.features.auth.usecase.FetchUserInfoUseCase
import com.halill.domain.features.auth.usecase.LogoutUseCase
import com.halill.domain.features.auth.usecase.SaveUserNameUseCase
import com.halill.domain.features.todo.usecase.FetchAllTimeTodoUseCase
import com.halill.domain.features.todo.usecase.FetchCurrentCountUseCase
import com.halill.halill.base.BaseViewModel
import com.halill.halill.base.EventFlow
import com.halill.halill.base.MutableEventFlow
import com.halill.halill.base.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val fetchUserInfoUseCase: FetchUserInfoUseCase,
    private val fetchCurrentCountUseCase: FetchCurrentCountUseCase,
    private val fetchAllTimeTodoUseCase: FetchAllTimeTodoUseCase
) : BaseViewModel<MyPageState, MyPageEvent>() {
    override val initialState: MyPageState
        get() = MyPageState.initial()

    private val _myPageViewEffect = MutableEventFlow<MyPageViewEffect>()
    val myPageViewEffect: EventFlow<MyPageViewEffect> = _myPageViewEffect.asEventFlow()

    suspend fun logout() {
        logoutUseCase.execute(Unit)
        _myPageViewEffect.emit(MyPageViewEffect.StartLogin)
    }

    suspend fun fetchUserInfo() {
        fetchUserInfoUseCase.execute(Unit).collect { user ->
            sendEvent(MyPageEvent.SetUser(user))
        }
    }

    suspend fun fetchCurrentCount() {
        val data = fetchCurrentCountUseCase.execute(Unit)
        sendEvent(MyPageEvent.SetCurrentTodoCount(data.todoCount, data.doneCount))
    }

    suspend fun fetchAllTimeCount() {
        fetchAllTimeTodoUseCase.execute(Unit).collect {
            sendEvent(MyPageEvent.SetAllTimeTodoCount(it.allCount, it.allDoneCount))
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            saveUserNameUseCase.execute(name).collect { isSuccess ->
                if (isSuccess) {
                    fetchUserInfo()
                } else {
                    _myPageViewEffect.emit(MyPageViewEffect.SaveNameFailed)
                }
            }
        }
    }

    fun showSaveNameDialog() {
        sendEvent(MyPageEvent.ShowSaveNameDialog)
    }

    fun dismissSaveNameDialog() {
        sendEvent(MyPageEvent.DismissSaveNameDialog)
    }

    fun showLogoutDialog() {
        sendEvent(MyPageEvent.ShowLogoutDialog)
    }

    fun dismissLogoutDialog() {
        sendEvent(MyPageEvent.DismissLogoutDialog)
    }

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {
        when (event) {
            is MyPageEvent.SetAllTimeTodoCount -> {
                setState(
                    oldState.copy(
                        allCount = event.allCount,
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

            is MyPageEvent.DismissSaveNameDialog -> {
                setState(
                    oldState.copy(
                        showSaveNameDialog = false
                    )
                )
            }

            is MyPageEvent.ShowSaveNameDialog -> {
                setState(
                    oldState.copy(
                        showSaveNameDialog = true
                    )
                )
            }

            is MyPageEvent.DismissLogoutDialog -> {
                setState(
                    oldState.copy(
                        showLogoutDialog = false
                    )
                )
            }
            is MyPageEvent.ShowLogoutDialog -> {
               setState(
                   oldState.copy(
                       showLogoutDialog = true
                   )
               )
            }
        }
    }
}