package com.example.events.ui.utils
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    fun convertTimestampToDateString(timestamp: Long): String {
        val dateFormatted = SimpleDateFormat("MM/dd/yyyy")
        val date = Date(timestamp * 1000)
        return dateFormatted.format(date).capitalize()
    }
}