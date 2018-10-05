package com.wanari.meetingtimer.di

import com.wanari.meetingtimer.navigation.Screen
import com.wanari.meetingtimer.navigation.screens.StartScreen
import org.koin.dsl.module.module

val configModule = module {
    // Define the default starting Screen
    single { StartScreen() as Screen }
}
