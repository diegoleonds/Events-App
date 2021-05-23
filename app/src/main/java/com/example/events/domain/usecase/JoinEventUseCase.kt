package com.example.events.domain.usecase

import com.example.events.data.model.Event
import com.example.events.data.model.Person
import com.example.events.data.repository.EventRepositoryImpl
import com.example.events.domain.repository.EventRepository
import com.example.events.domain.transform.EventPersonTransform

class JoinEventUseCase(
    val repository: EventRepositoryImpl,
    val transform: EventPersonTransform
) {
    suspend fun joinEvent(event: Event, person: Person){
        repository.joinEvent(transform.transformIntoEventPerson(event, person))
    }
}