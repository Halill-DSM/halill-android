package com.halill.halill.features.mypage

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.halill.base.MviEvent

sealed class MyPageEvent : MviEvent {
    data class SetCurrentTodoCount(val currentTodoCount: Int, val currentDoneCount: Int) : MyPageEvent()
    data class SetAllTimeTodoCount(val allTimeTodoCount: Int, val allTimeDoneCount: Int) : MyPageEvent()
    data class SetUser(val userEntity: UserEntity) : MyPageEvent ()
    object ShowSaveNameDialog : MyPageEvent()
    object DismissSaveNameDialog : MyPageEvent()
}