package com.wanari.meetingtimer.di

import com.vanniktech.rxpermission.RealRxPermission
import com.vanniktech.rxpermission.RxPermission
import org.koin.dsl.module.module

val permissionModule = module {
    single { RealRxPermission.getInstance(get()) as RxPermission }
}
