package com.halill.halill.features.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.domain.features.auth.entity.UserEntity
import com.halill.halill.base.observeWithLifecycle

@Composable
fun MyPage(
    navController: NavController,
    userEntity: UserEntity,
    viewModel: MyPageViewModel = hiltViewModel()
) {

    LaunchedEffect(userEntity) {
        viewModel.setUser(userEntity)
    }

    viewModel.myPageViewEffect.observeWithLifecycle {
        when (it) {
            is MyPageViewEffect.StartLogin -> {
                navController.navigate("login")
            }
        }
    }
}