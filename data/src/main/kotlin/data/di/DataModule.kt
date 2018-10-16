package data.di

import data.interactor.DefaultLogInInteractor
import data.interactor.DefaultNewsInteractor
import data.interactor.DefaultSignUpInteractor
import interactor.LogInInteractor
import interactor.NewsInteractor
import interactor.SignUpInteractor
import org.koin.dsl.module.module

val dataModule = module {
    single { DefaultLogInInteractor(get(), get()) as LogInInteractor }
    single { DefaultSignUpInteractor(get(), get()) as SignUpInteractor }
    single { DefaultNewsInteractor(get(), get()) as NewsInteractor }
}