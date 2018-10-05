package com.wanari.meetingtimer.data.di

import com.wanari.meetingtimer.action.CalendarAction
import com.wanari.meetingtimer.action.TimerAction
import com.wanari.meetingtimer.data.action.MockCalendarAction
import com.wanari.meetingtimer.data.bridge.DefaultTimerBridge
import com.wanari.meetingtimer.data.store.MockCalendarStore
import com.wanari.meetingtimer.store.CalendarStore
import com.wanari.meetingtimer.store.TimerStore
import org.koin.dsl.module.module

val dataModule = module {
    single { MockCalendarAction(get()) as CalendarAction }
    single { MockCalendarStore(get()) as CalendarStore }

    single { DefaultTimerBridge(get()) }.apply {
        bind(TimerAction::class)
        bind(TimerStore::class)
    }
}
