package com.example.events.data.repository

import com.example.events.data.api.RetrofitInstance
import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson
import com.example.events.domain.repository.EventRepository

class EventRepositoryImpl : EventRepository {
    override suspend fun getEventById(id: Long): Event =  RetrofitInstance.api.getEventById(id)

    override suspend fun getAllEvents(): List<Event> {
        try {
            return RetrofitInstance.api.getAllEvents()
        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun joinEvent(eventPerson: EventPerson) =  RetrofitInstance.api.joinEvent(eventPerson)
}