package com.example.events.data.api

import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @GET("events/{id}}")
    suspend fun getEventById(@Path("id") id: Long): Event

    @GET("events")
    suspend fun getAllEvents(): List<Event>

    @POST("checkin")
    suspend fun joinEvent(@Body eventPerson: EventPerson)
}