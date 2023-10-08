package com.mars.cloveranime.domain

import com.mars.cloveranime.data.CalendarRepository
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.data.model.SearchAnimeModel

class CalendarUseCase {
    val repository = CalendarRepository()
    suspend operator fun invoke(day: Int): MutableList<CalendarModel>? = repository.getCalendar(day)
}