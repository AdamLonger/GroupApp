@file:Suppress("StringLiteralDuplication", "MagicNumber")

package com.wanari.meetingtimer.data.generators

import com.wanari.meetingtimer.data.store.MockCalendarStore
import com.wanari.meetingtimer.model.Attendee
import com.wanari.meetingtimer.model.Event
import org.threeten.bp.LocalDateTime
import java.util.*

internal fun generateAttendees() = lazy {
    listOf(
            Attendee("Reka | MobileDev | Wanari", "a@mail.com"),
            Attendee("Pogi | MobileDev | Wanari", "b@mail.com"),
            Attendee("Karolina | Partner | Wanari", "a@mail.com"),
            Attendee("Norbi | CTO | Wanari", "b@mail.com"),
            Attendee("Rege | CEO | Wanari", "a@mail.com"),
            Attendee("Meli | PM | Wanari", "b@mail.com"),
            Attendee("Renato | MobileDev | Wanari", "c@mail.com"),
            Attendee("Agocs | MobileDev | Wanari", "a@mail.com"),
            Attendee("Picimaci | FrontendDev | Wanari", "b@mail.com"),
            Attendee("Papi | BackendDev | Wanari", "a@mail.com"),
            Attendee("A a | MobileDev | Wanari", "a@mail.com"),
            Attendee("B b | BackendDev | Wanari", "b@mail.com"),
            Attendee("C c | BackendDev | Wanari", "c@mail.com"),
            Attendee("A a | MobileDev | Wanari", "a@mail.com"),
            Attendee("B b | BackendDev | Wanari", "b@mail.com"),
            Attendee("C c | BackendDev | Wanari", "c@mail.com"),
            Attendee("A a | MobileDev | Wanari", "a@mail.com"),
            Attendee("B b | BackendDev | Wanari", "b@mail.com"),
            Attendee("C c | BackendDev | Wanari", "c@mail.com"),
            Attendee("", "a@mail.com"),
            Attendee("", "b@gmail.com"),
            Attendee("", "c@msn.com"),
            Attendee("", "d@gmail.com"),
            Attendee("", "e@mail.com"),
            Attendee("", "f@gmail.com"),
            Attendee("", "g@citromail.com"),
            Attendee("", "h@freemail.hu"),
            Attendee("", "i@mail.com")
    )
}

internal fun MockCalendarStore.generateMockEvents() = lazy {
    listOf(
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Workshop",
                    location = "Meeting room",
                    description = null,
                    start = LocalDateTime.now().plusHours(2),
                    end = LocalDateTime.now().plusHours(3),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Staff Meeting",
                    location = "Lobby",
                    description = null,
                    start = LocalDateTime.now().plusHours(3),
                    end = LocalDateTime.now().plusHours(4),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "1 on 1",
                    location = "Catering room",
                    description = null,
                    start = LocalDateTime.now().plusHours(4),
                    end = LocalDateTime.now().plusHours(5),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Wanari Wednesday with all the Alumni Group",
                    location = "Deák tér, Fröccs terasz",
                    description = null,
                    start = LocalDateTime.now().plusHours(2),
                    end = LocalDateTime.now().plusHours(3),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Squad Lunch",
                    location = "Budapest, Madách tér 2. Castro Bistro külső terasz",
                    description = null,
                    start = LocalDateTime.now().plusHours(2),
                    end = LocalDateTime.now().plusHours(3),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Spring Planning",
                    location = "Meeting Room",
                    description = null,
                    start = LocalDateTime.now().plusHours(1),
                    end = LocalDateTime.now().plusHours(2),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Retro",
                    location = "Lobby Room",
                    description = null,
                    start = LocalDateTime.now().plusHours(2),
                    end = LocalDateTime.now().plusHours(3),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Just Now",
                    location = "ASAP Room",
                    description = null,
                    start = LocalDateTime.now().minusMinutes(10),
                    end = LocalDateTime.now().plusMinutes(2),
                    attendees = attendees
            ),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Very Important and long named Staff Meeting",
                    location = "Very long location named Lobby 123 actually longer than this thanks",
                    description = null,
                    start = LocalDateTime.now().plusHours(3),
                    end = LocalDateTime.now().plusHours(4),
                    attendees = attendees),
            Event(
                    id = UUID.randomUUID().toString(),
                    name = "Ongoing Screen Test",
                    location = "For the horde!",
                    description = null,
                    start = LocalDateTime.now().plusSeconds(12),
                    end = LocalDateTime.now().plusSeconds(48),
                    attendees = attendees)
    )
}
