package com.wanari.meetingtimer.common.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.View
import com.wanari.meetingtimer.navigation.Screen

abstract class ScreenFragment : BaseFragment() {

    protected val screen: Screen by lazy {
        arguments
                ?.let { it[ARG_SCREEN] as? Screen }
                ?: throw IllegalStateException("Screen not found in Bundle.")
    }

    @CallSuper
    open fun onBackPressed() = false

    @Suppress("UNCHECKED_CAST")
    protected fun <S : Screen> screen() = screen as S

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val ARG_SCREEN = "ArgScreen"
    }
}
