package com.longer.groupapp.common.ui

import android.support.annotation.CallSuper
import com.longer.groupapp.common.mvi.MviBaseFragment
import com.longer.groupapp.common.mvi.MviView
import com.longer.groupapp.navigation.Screen

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
