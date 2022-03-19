package com.halill.halill.features.mypage

import com.halill.halill.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : BaseViewModel<MyPageState, MyPageEvent>() {
    override val initialState: MyPageState
        get() = MyPageState.initial()

    override fun reduceEvent(oldState: MyPageState, event: MyPageEvent) {
        when(event) {
            
        }
    }

}