package com.example.events.domain.usecase

import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson
import com.example.events.data.model.Person
import com.example.events.data.repository.EventRepositoryImpl
import com.example.events.domain.exception.PersonAlreadyInEventException
import com.example.events.domain.repository.EventRepository
import com.example.events.domain.transform.EventPersonTransform
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import junit.framework.Assert.fail
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import org.junit.Test

class JoinEventUseCaseTest {
    val transform = EventPersonTransform()
    val mockRepository = mockk<EventRepositoryImpl>()
    val useCase = JoinEventUseCase(mockRepository, transform)
    val person = Person(
        "name",
        "email"
    )
    val event = Event(
        1,
        "title",
        "description",
        "image",
        2.0,
        1212121,
        ArrayList<Person>()
    )

    @Before
    fun mockRepositoryMethod() {
        coEvery { mockRepository.joinEvent(any()) } just runs
    }

    @Test
    fun shouldThrowPersonAlreadyInEventException() = runBlocking {
        try {
            val people =  ArrayList<Person>()
            people.add(person)
            val eventWithPeople = event.copy(people = people)
            useCase.joinEvent(eventWithPeople, person)
            fail("should have throw exception: ${PersonAlreadyInEventException().message}")
        } catch (e: PersonAlreadyInEventException) {
        }
    }

    @Test
    fun shouldNotThrowPersonAlreadyInEventExceptionWhenEventPeopleListIsEmpty() = runBlocking {
        try {
            useCase.joinEvent(event, person)
        } catch (e: PersonAlreadyInEventException) {
            fail("should not have throw exception: ${e.message}")
        }
    }

    @Test
    fun shouldNotThrowPersonAlreadyInEventExceptionWhenPersonIsNotInTheEvent() = runBlocking {
        try {
            val people =  ArrayList<Person>()
            people.add(person)
            val eventWithPeople = event.copy(people = people)
            useCase.joinEvent(event, person.copy(name = "other person"))
        } catch (e: PersonAlreadyInEventException) {
            fail("should not have throw exception: ${e.message}")
        }
    }
}