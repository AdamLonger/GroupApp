package com.longer.groupapp.common.di

import android.os.Bundle
import com.longer.groupapp.common.ui.BaseActivity
import com.longer.groupapp.common.ui.ScreenFragment
import com.longer.groupapp.navigation.Screen
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
