package com.wanari.meetingtimer.presentation.di

import com.wanari.meetingtimer.common.di.declareScreen
import com.wanari.meetingtimer.navigation.screens.*
import com.wanari.meetingtimer.presentation.login.LogInPresenter
import com.wanari.meetingtimer.presentation.login.LogInScreenFragment
import com.wanari.meetingtimer.presentation.news.NewsPresenter
import com.wanari.meetingtimer.presentation.news.NewsScreenFragment
import com.wanari.meetingtimer.presentation.news.paging.NewsDataProvider
import com.wanari.meetingtimer.presentation.newspage.NewsPagePresenter
import com.wanari.meetingtimer.presentation.newspage.NewsPageScreenFragment
import com.wanari.meetingtimer.presentation.settings.SettingsPresenter
import com.wanari.meetingtimer.presentation.settings.SettingsScreenFragment
import com.wanari.meetingtimer.presentation.signup.SignUpPresenter
import com.wanari.meetingtimer.presentation.signup.SignUpScreenFragment
import org.koin.dsl.module.module

val screenModule = module {
    declareScreen<LogInScreen> { LogInScreenFragment() }
    factory { LogInPresenter(it[0], get()) }

    declareScreen<SignUpScreen> { SignUpScreenFragment() }
    factory { SignUpPresenter(it[0], get()) }

    declareScreen<NewsScreen> { NewsScreenFragment() }
    factory { NewsPresenter(it[0], get()) }
    factory { NewsDataProvider(get()) }

    declareScreen<NewsPageScreen> { NewsPageScreenFragment() }
    factory { NewsPagePresenter(it[0], get()) }

    declareScreen<SettingsScreen> { SettingsScreenFragment() }
    factory { SettingsPresenter(it[0], get()) }
}
