package com.wanari.meetingtimer.common.di

import android.os.Bundle
import com.wanari.meetingtimer.common.ui.BaseActivity
import com.wanari.meetingtimer.common.ui.ScreenFragment
import com.wanari.meetingtimer.navigation.Screen
import org.koin.android.ext.android.get
import org.koin.core.parameter.ParameterList
import org.koin.dsl.context.ModuleDefinition

typealias ScreenFactory = () -> ScreenFragment<*, *>

inline fun <reified S : Screen> ModuleDefinition.declareScreen(crossinline screenFactory: ScreenFactory) {
    factory(name = S::class.java.canonicalName) {
        screenFactory().apply {
            setArguments(Bundle().apply { putParcelable(ScreenFragment.ARG_SCREEN, it[0]) })
        }
    }
}

fun BaseActivity.createScreen(screen: Screen): ScreenFragment<*, *> =
        get(name = screen.javaClass.canonicalName) { ParameterList(screen) }
