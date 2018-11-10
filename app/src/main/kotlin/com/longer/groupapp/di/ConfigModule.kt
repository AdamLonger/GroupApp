package com.longer.groupapp.di

import com.longer.groupapp.navigation.Screen
import com.longer.groupapp.navigation.screens.LogInScreen
import org.koin.dsl.module.module

val configModule = module {
    // Define the default starting Screen
    single { LogInScreen() as Screen }
}
