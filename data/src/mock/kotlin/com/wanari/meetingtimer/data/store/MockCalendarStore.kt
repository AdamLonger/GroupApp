package com.wanari.meetingtimer.data.store

import com.wanari.meetingtimer.common.rx.Schedulers
import com.wanari.meetingtimer.data.generators.generateAttendees
import com.wanari.meetingtimer.data.generators.generateMockEvents
import com.wanari.meetingtimer.data.utils.REFRESH_DELAY
import com.wanari.meetingtimer.model.Attendee
import com.wanari.meetingtimer.model.Event
import com.wanari.meetingtimer.store.CalendarStore
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class MockCalendarStore(
        private val schedulers: Schedulers
) : CalendarStore {

    private val eventList: List<Event> by generateMockEvents()
    internal val attendees: List<Attendee> by generateAttendees()

    override fun getEvents(): Flowable<List<Event>> {
        return Flowable.just(eventList)
                .delay(REFRESH_DELAY, TimeUnit.SECONDS)
                .subscribeOn(schedulers.computation())
    }

    override fun getEvent(eventId: String): Flowable<Event> {
        return Flowable.fromIterable(eventList)
                .filter { it.id == eventId }
                .firstElement()
                .toFlowable()
                .subscribeOn(schedulers.computation())
    }
}
