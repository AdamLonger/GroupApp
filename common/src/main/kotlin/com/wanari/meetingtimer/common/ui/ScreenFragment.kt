package com.wanari.meetingtimer.common.ui

import android.support.annotation.CallSuper
import com.wanari.meetingtimer.common.mvi.MviBaseFragment
import com.wanari.meetingtimer.common.mvi.MviView
import com.wanari.meetingtimer.navigation.Screen

abstract class ScreenFragment<V : MviView<VS>, VS : Any> : MviBaseFragment<V, VS>() {

    protected val screen: Screen by lazy {
        arguments
                ?.let { it[ARG_SCREEN] as? Screen }
                ?: throw IllegalStateException("Screen not found in Bundle.")
    }

    @CallSuper
    open fun onBackPressed() = false

    @Suppress("UNCHECKED_CAST")
    protected fun <S : Screen> screen() = screen as S

    companion object {
        const val ARG_SCREEN = "ArgScreen"
    }
}
