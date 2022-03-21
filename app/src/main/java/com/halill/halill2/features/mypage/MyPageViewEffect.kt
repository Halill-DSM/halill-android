package com.halill.halill2.features.mypage

sealed class MyPageViewEffect {

    object StartLogin : MyPageViewEffect()
    object SaveNameFailed : MyPageViewEffect()
}