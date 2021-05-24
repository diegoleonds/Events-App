package com.example.events.domain.exception

class PersonAlreadyInEventException(message: String = "This person already join this event"):
    Exception(message) {
}