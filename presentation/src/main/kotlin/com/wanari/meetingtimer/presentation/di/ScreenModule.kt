package com.wanari.meetingtimer.presentation.di

import com.wanari.meetingtimer.common.di.declareScreen
import com.wanari.meetingtimer.navigation.screens.*
import com.wanari.meetingtimer.presentation.grouppage.GroupPagePresenter
import com.wanari.meetingtimer.presentation.grouppage.GroupPageScreenFragment
import com.wanari.meetingtimer.presentation.grouppage.paging.GroupNewsDataProvider
import com.wanari.meetingtimer.presentation.groups.GroupsPresenter
import com.wanari.meetingtimer.presentation.groups.GroupsScreenFragment
import com.wanari.meetingtimer.presentation.groups.paging.GroupsDataProvider
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
import com.wanari.meetingtimer.presentation.usergroups.UserGroupsPresenter
import com.wanari.meetingtimer.presentation.usergroups.UserGroupsScreenFragment
import com.wanari.meetingtimer.presentation.usergroups.paging.UserGroupsDataProvider
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

    declareScreen<GroupsScreen> { GroupsScreenFragment() }
    factory { GroupsPresenter(it[0], get()) }
    factory { GroupsDataProvider(get()) }

    declareScreen<GroupPageScreen> { GroupPageScreenFragment() }
    factory { GroupPagePresenter(it[0], get()) }
    factory { GroupNewsDataProvider(get()) }

    declareScreen<UserGroupsScreen> { UserGroupsScreenFragment() }
    factory { UserGroupsPresenter(it[0], get()) }
    factory { UserGroupsDataProvider(get()) }

    declareScreen<SettingsScreen> { SettingsScreenFragment() }
    factory { SettingsPresenter(it[0], get()) }
}
