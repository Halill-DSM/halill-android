package com.halill.halill.features.mypage

sealed class MyPageViewEffect {

    object StartLogin : MyPageViewEffect()
    object SaveNameFailed : MyPageViewEffect()
}