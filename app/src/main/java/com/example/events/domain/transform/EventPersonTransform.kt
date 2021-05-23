package com.example.events.domain.transform

import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson
import com.example.events.data.model.Person

class EventPersonTransform {
    fun transformIntoEventPerson(event: Event, person: Person): EventPerson {
        return EventPerson(
            event.id,
            person.name,
            person.email
        )
    }
}