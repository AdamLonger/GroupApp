package com.wanari.meetingtimer.common.di

import com.wanari.meetingtimer.common.rx.RxSchedulers
import com.wanari.meetingtimer.common.rx.Schedulers
import org.koin.dsl.module.module

val schedulerModule = module {
    single { RxSchedulers() as Schedulers }
}
