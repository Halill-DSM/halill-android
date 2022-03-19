package com.halill.halill.main

import com.halill.domain.features.auth.entity.UserEntity
import com.halill.halill.base.MviState

data class MainState(
    val userEntity: UserEntity,
    val isLoading: Boolean,
    val needLogin: Boolean
) : MviState {
    companion object {
        fun initial() = MainState(
            userEntity = UserEntity("",""),
            isLoading = true,
            needLogin = false
        )
    }
}
