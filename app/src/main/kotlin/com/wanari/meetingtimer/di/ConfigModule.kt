package com.wanari.meetingtimer.di

import com.wanari.meetingtimer.navigation.Screen
import com.wanari.meetingtimer.navigation.screens.LogInScreen
import org.koin.dsl.module.module

val configModule = module {
    // Define the default starting Screen
    single { LogInScreen() as Screen }
}
