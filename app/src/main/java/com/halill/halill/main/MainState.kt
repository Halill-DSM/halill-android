package com.halill.halill.main

import com.halill.halill.base.MviState

data class MainState(
    val userName: String,
    val userEmail: String,
    val isLoading: Boolean
) : MviState {
    companion object {
        fun initial() = MainState(
            userName = "",
            userEmail = "",
            isLoading = true
        )
    }
}
