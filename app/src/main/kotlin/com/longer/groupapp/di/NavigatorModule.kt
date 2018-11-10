package com.longer.groupapp.di

import com.longer.groupapp.navigation.DefaultNavigator
import com.longer.groupapp.navigation.FragmentScreenManager
import com.longer.groupapp.navigation.Navigator
import com.longer.groupapp.navigation.ScreenManager
import org.koin.dsl.module.module

val navigatorModule = module {
    single { DefaultNavigator(get()) as Navigator }
    single { FragmentScreenManager(get()) as ScreenManager }
}
