package data.di

import data.interactor.DefaultSignInInteractor
import data.interactor.DefaultSignUpInteractor
import interactor.SignInInteractor
import interactor.SignUpInteractor
import org.koin.dsl.module.module

val dataModule = module {
    single { DefaultSignInInteractor(get(), get()) as SignInInteractor }
    single { DefaultSignUpInteractor(get(), get()) as SignUpInteractor }
}