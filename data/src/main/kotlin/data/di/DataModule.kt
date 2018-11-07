package data.di

import data.interactor.*
import interactor.*
import org.koin.dsl.module.module

val dataModule = module {
    single { DefaultLogInInteractor(get(), get()) as LogInInteractor }
    single { DefaultSignUpInteractor(get(), get()) as SignUpInteractor }
    single { DefaultNewsInteractor(get()) as NewsInteractor }
    single { DefaultNewsPageInteractor(get()) as NewsPageInteractor }
    single { DefaultGroupsInteractor(get()) as GroupsInteractor }
    single { DefaultGroupPageInteractor(get(), get(), get()) as GroupPageInteractor }
    single { DefaultSettingsInteractor(get(), get(), get()) as SettingsInteractor }
    single { DefaultUserGroupsInteractor(get()) as UserGroupsInteractor }
}