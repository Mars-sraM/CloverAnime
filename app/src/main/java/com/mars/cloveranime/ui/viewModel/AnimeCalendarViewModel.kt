package com.mars.cloveranime.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.domain.CalendarUseCase
import kotlinx.coroutines.launch

class AnimeCalendarViewModel : ViewModel() {
    val calendar = MutableLiveData<MutableList<CalendarModel>?>()
    val isloading = MutableLiveData<Boolean>()
    val calendarUseCase = CalendarUseCase()
    fun addListAnimes(day: Int) {
        viewModelScope.launch {
            isloading.postValue(false)
            val animes = calendarUseCase(day)
            calendar.postValue(animes)
            isloading.postValue(true)
        }

    }


}