package com.halill.halill2.features.mypage

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.halill2.base.MviEvent

sealed class MyPageEvent : MviEvent {
    data class SetCurrentTodoCount(val currentTodoCount: Int, val currentDoneCount: Int) : MyPageEvent()
    data class SetAllTimeTodoCount(val allCount: Int, val allTimeDoneCount: Int) : MyPageEvent()
    data class SetUser(val userEntity: UserEntity) : MyPageEvent ()
    object ShowSaveNameDialog : MyPageEvent()
    object DismissSaveNameDialog : MyPageEvent()
    object ShowLogoutDialog : MyPageEvent()
    object DismissLogoutDialog : MyPageEvent()
    object StartLoading : MyPageEvent()
    object FinishLoading : MyPageEvent()
}