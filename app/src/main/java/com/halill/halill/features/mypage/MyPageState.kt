package com.halill.halill.features.mypage

import com.halill.halill.base.MviState

data class MyPageState(
    val name: String,
    val email: String,
    val showSaveNameDialog: Boolean,
    val showLogoutDialog: Boolean,
    val currentTodoCount: Int,
    val currentDoneCount: Int,
    val isLoading: Boolean,
    val allCount: Int,
    val allTimeDoneTodoCount: Int
) : MviState {

    companion object {
        fun initial(): MyPageState =
            MyPageState(
                name = "",
                email = "",
                showSaveNameDialog = false,
                showLogoutDialog = false,
                currentTodoCount = 0,
                currentDoneCount = 0,
                isLoading = false,
                allCount = 0,
                allTimeDoneTodoCount = 0
            )
    }
}