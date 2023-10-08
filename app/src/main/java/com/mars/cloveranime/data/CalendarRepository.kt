package com.mars.cloveranime.data
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.data.network.CalendarRequest

class CalendarRepository {
    private val jsoupResponse = CalendarRequest()
    suspend fun getCalendar(day: Int): MutableList<CalendarModel> {
        val result = jsoupResponse.requestCalendar(day)
        return result
    }
}