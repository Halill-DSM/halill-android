package com.halill.halill.features.mypage

import com.halill.halill.base.MviState

data class MyPageState(
    val name: String,
    val email: String,
    val currentTodoCount: Int,
    val currentDoneCount: Int,
    val allTimeTodoCount: Int,
    val allTimeDoneTodoCount: Int
) : MviState {

    companion object {
        fun initial(): MyPageState =
            MyPageState(
                name = "",
                email = "",
                currentTodoCount = 0,
                currentDoneCount = 0,
                allTimeTodoCount = 0,
                allTimeDoneTodoCount = 0
            )
    }
}