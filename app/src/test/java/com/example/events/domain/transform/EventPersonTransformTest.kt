package com.example.events.domain.transform

import com.example.events.data.model.Event
import com.example.events.data.model.EventPerson
import com.example.events.data.model.Person
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.Test

class EventPersonTransformTest {
    val transform = EventPersonTransform()
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
    val eventPerson = EventPerson(
        event.id,
        person.name,
        person.email
    )

    @Test
    fun shouldReturnExpectedEventPerson() {
        assertEquals(eventPerson, transform.transformIntoEventPerson(event, person))
    }

    @Test
    fun shouldNotReturnExpectedEventPerson() {
        assertNotEquals(eventPerson, transform.transformIntoEventPerson(event.copy(id = 2), person))
    }
}