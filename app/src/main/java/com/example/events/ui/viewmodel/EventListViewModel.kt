package com.example.events.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.events.data.api.RetrofitInstance
import com.example.events.data.model.Event
import com.example.events.data.repository.EventRepositoryImpl
import com.example.events.domain.repository.EventRepository

class EventListViewModel(
    val repository: EventRepositoryImpl
) : ViewModel() {
    val events = MutableLiveData<List<Event>>()

    suspend fun getEvents() {
        events.postValue(repository.getAllEvents())
    }
}