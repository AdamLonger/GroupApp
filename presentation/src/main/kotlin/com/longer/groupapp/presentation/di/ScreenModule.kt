package com.longer.groupapp.presentation.di

import com.longer.groupapp.common.di.declareScreen
import com.longer.groupapp.navigation.screens.*
import com.longer.groupapp.presentation.grouppage.GroupPagePresenter
import com.longer.groupapp.presentation.grouppage.GroupPageScreenFragment
import com.longer.groupapp.presentation.grouppage.paging.GroupNewsDataProvider
import com.longer.groupapp.presentation.groups.GroupsPresenter
import com.longer.groupapp.presentation.groups.GroupsScreenFragment
import com.longer.groupapp.presentation.groups.paging.GroupsDataProvider
import com.longer.groupapp.presentation.login.LogInPresenter
import com.longer.groupapp.presentation.login.LogInScreenFragment
import com.longer.groupapp.presentation.news.NewsPresenter
import com.longer.groupapp.presentation.news.NewsScreenFragment
import com.longer.groupapp.presentation.news.paging.NewsDataProvider
import com.longer.groupapp.presentation.newspage.NewsPagePresenter
import com.longer.groupapp.presentation.newspage.NewsPageScreenFragment
import com.longer.groupapp.presentation.settings.SettingsPresenter
import com.longer.groupapp.presentation.settings.SettingsScreenFragment
import com.longer.groupapp.presentation.signup.SignUpPresenter
import com.longer.groupapp.presentation.signup.SignUpScreenFragment
import com.longer.groupapp.presentation.usergroups.UserGroupsPresenter
import com.longer.groupapp.presentation.usergroups.UserGroupsScreenFragment
import com.longer.groupapp.presentation.usergroups.paging.UserGroupsDataProvider
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
