package data.di

import data.interactor.*
import interactor.*
import org.koin.dsl.module.module

val dataModule = module {
    single { DefaultLogInInteractor(get(), get()) as LogInInteractor }
    single { DefaultSignUpInteractor(get(), get()) as SignUpInteractor }
    single { DefaultNewsInteractor(get()) as NewsInteractor }
    single { DefaultNewsPageInteractor(get()) as NewsPageInteractor }
    single { DefaultSettingsInteractor(get(), get()) as SettingsInteractor }
}