package com.wanari.meetingtimer.presentation.di

import com.wanari.meetingtimer.common.di.declareScreen
import com.wanari.meetingtimer.navigation.screens.StartScreen
import com.wanari.meetingtimer.presentation.view.StartScreenFragment
import org.koin.dsl.module.module


val screenModule = module {
    declareScreen<StartScreen> { StartScreenFragment() }
}
