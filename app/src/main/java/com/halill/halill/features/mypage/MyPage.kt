package com.halill.halill.features.mypage

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.halill.domain.features.auth.entity.UserEntity

@Composable
fun MyPage(
    navController: NavController,
    userEntity: UserEntity,
    viewModel: MyPageViewModel = hiltViewModel()
) {


}