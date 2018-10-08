package data.di

import data.firebase.DefaultLoginManager
import managers.LoginManager
import org.koin.dsl.module.module

val dataModule = module {
    single { DefaultLoginManager(get(), get()) as LoginManager }
}