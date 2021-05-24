package com.example.events.ui.utils

import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.Assert.assertNotEquals
import org.junit.Test

class DateConverterTest {
    val convert = DateConverter()

    @Test
    fun shouldReturnExpectedDate() {
        val expectedDate = "05/24/2021"
        assertEquals(expectedDate, convert.convertTimestampToDateString(1621825933L))
    }

    @Test
    fun shouldNotReturnExpectedDate() {
        val expectedDate = "06/24/2021"
        assertNotEquals(expectedDate, convert.convertTimestampToDateString(1621825933L))
    }
}