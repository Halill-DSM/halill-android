package com.halill.halill.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.halill.domain.features.auth.entity.UserEntity

@Composable
fun MyPage(userEntity: UserEntity) {
    Column {
        Text(text = userEntity.email)
        Text(text = userEntity.name)
    }

}