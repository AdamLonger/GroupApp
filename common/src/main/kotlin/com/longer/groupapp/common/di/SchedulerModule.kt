package com.longer.groupapp.common.di

import com.longer.groupapp.common.rx.RxSchedulers
import com.longer.groupapp.common.rx.Schedulers
import org.koin.dsl.module.module

val schedulerModule = module {
    single { RxSchedulers() as Schedulers }
}
