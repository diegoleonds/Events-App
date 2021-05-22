package com.example.events.domain.repository

import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson

interface EventRepository {

    suspend fun getEventById(id : Long) : Event
    suspend fun getAllEvents() : List<Event>
    suspend fun joinEvent(eventPerson: EventPerson)
}