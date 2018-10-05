package com.wanari.meetingtimer.data.action

import com.wanari.meetingtimer.action.CalendarAction
import com.wanari.meetingtimer.common.rx.Schedulers
import com.wanari.meetingtimer.data.utils.REFRESH_DELAY
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class MockCalendarAction(
        private val schedulers: Schedulers
) : CalendarAction {

    override fun refresh(): Completable {
        return Completable.complete()
                .delay(REFRESH_DELAY, TimeUnit.SECONDS)
                .subscribeOn(schedulers.io())
    }
}
