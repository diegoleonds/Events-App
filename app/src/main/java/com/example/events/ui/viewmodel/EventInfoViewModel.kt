package com.example.events.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson
import com.example.events.data.model.Person
import com.example.events.data.repository.EventRepositoryImpl
import com.example.events.domain.usecase.JoinEventUseCase

class EventInfoViewModel(
    val joinUseCase: JoinEventUseCase
): ViewModel() {

    suspend fun joinEvent(event: Event, person: Person) {
        joinUseCase.joinEvent(event, person)
    }
}