package com.wanari.meetingtimer.presentation.di

import com.wanari.meetingtimer.common.di.declareScreen
import com.wanari.meetingtimer.navigation.screens.StartScreen
import com.wanari.meetingtimer.presentation.signin.StartPresenter
import com.wanari.meetingtimer.presentation.signin.StartScreenFragment
import org.koin.dsl.module.module


val screenModule = module {
    declareScreen<StartScreen> { StartScreenFragment() }
    factory { StartPresenter(it[0], get()) }
}
