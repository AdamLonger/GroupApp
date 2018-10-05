package com.wanari.meetingtimer.di

import com.wanari.meetingtimer.navigation.DefaultNavigator
import com.wanari.meetingtimer.navigation.FragmentScreenManager
import com.wanari.meetingtimer.navigation.Navigator
import com.wanari.meetingtimer.navigation.ScreenManager
import org.koin.dsl.module.module

val navigatorModule = module {
    single { DefaultNavigator(get()) as Navigator }
    single { FragmentScreenManager(get()) as ScreenManager }
}
