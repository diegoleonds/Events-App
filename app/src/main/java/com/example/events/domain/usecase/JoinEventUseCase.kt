package com.example.events.domain.usecase

import com.example.events.data.model.Event
import com.example.events.data.model.Person
import com.example.events.data.repository.EventRepositoryImpl
import com.example.events.domain.exception.PersonAlreadyInEventException
import com.example.events.domain.transform.EventPersonTransform
import java.lang.Exception

class JoinEventUseCase(
    val repository: EventRepositoryImpl,
    val transform: EventPersonTransform
) {
    suspend fun joinEvent(event: Event, person: Person) {
        if (!event.people.isEmpty()) {
            event.people.forEach {
                if (it == person)
                    throw PersonAlreadyInEventException()
            }
        }
        repository.joinEvent(transform.transformIntoEventPerson(event, person))
    }
}