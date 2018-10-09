package com.wanari.meetingtimer.common.mvi

import com.hannesdorfmann.mosby3.mvp.MvpView

interface MviView<VS> : MvpView {
    fun render(viewState: VS)
}
