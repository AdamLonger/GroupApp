package com.wanari.meetingtimer.common.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wanari.meetingtimer.navigation.Screen

abstract class ScreenFragment : BaseFragment() {

    abstract val layoutRes: Int

    protected val screen: Screen by lazy {
        arguments
                ?.let { it[ARG_SCREEN] as? Screen }
                ?: throw IllegalStateException("Screen not found in Bundle.")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    @CallSuper
    open fun onBackPressed() = false

    @Suppress("UNCHECKED_CAST")
    protected fun <S : Screen> screen() = screen as S

    companion object {
        const val ARG_SCREEN = "ArgScreen"
    }
}
