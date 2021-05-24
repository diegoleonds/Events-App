package com.example.events.ui.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    fun convertTimestampToDateString(timestamp: Long): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy")
        val netDate = Date(timestamp * 1000)
        return sdf.format(netDate)
    }
}