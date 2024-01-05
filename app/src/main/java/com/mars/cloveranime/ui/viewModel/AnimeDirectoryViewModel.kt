package com.mars.cloveranime.ui.viewModel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.domain.CalendarUseCase
import com.mars.cloveranime.domain.FlvDirectoryUseCase
import com.mars.cloveranime.domain.McDirectoryUseCase
import kotlinx.coroutines.launch

class AnimeDirectoryViewModel : ViewModel() {
    val calendar = MutableLiveData<MutableList<CalendarModel>?>()
    val animes = MutableLiveData<MutableList<SearchAnimeModel>?>()
    val pageAnimes = MutableLiveData<MutableList<SearchAnimeModel>?>()
    val isloading = MutableLiveData<Boolean>()
    val isloadingPage = MutableLiveData<Boolean>()
    val calendarUseCase = CalendarUseCase()
    val mcDirectoryUseCase = McDirectoryUseCase()
    val flvDirectoryUseCase = FlvDirectoryUseCase()
    fun addListAnimes(day: Int) {
        viewModelScope.launch {
            isloading.postValue(false)
            val animes = calendarUseCase(day)
            calendar.postValue(animes)
            isloading.postValue(true)
        }

    }
    fun addDirectoryAnimes(provider: String, category: String, genero: String, page: Int) {
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    isloading.postValue(false)
                    val listAnimes = mcDirectoryUseCase(category, genero, page)
                    animes.postValue(listAnimes)
                    isloading.postValue(true)
                }
                "AnimeFLV" -> {
                    isloading.postValue(false)
                    val listAnimes = flvDirectoryUseCase(category, genero, page)
                    animes.postValue(listAnimes)
                    isloading.postValue(true)
                }
            }

        }

    }
    fun addPageDirectoryAnimes(provider: String, category: String, genero: String, page: Int) {
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    isloadingPage.postValue(false)
                    pageAnimes.value = mutableListOf()
                    val listAnimes = mcDirectoryUseCase(category, genero, page)
                    pageAnimes.postValue(listAnimes)
                    isloadingPage.postValue(true)
                }
                "AnimeFLV" -> {
                    isloadingPage.postValue(false)
                    pageAnimes.value = mutableListOf()
                    val listAnimes = flvDirectoryUseCase(category, genero, page)
                    pageAnimes.postValue(listAnimes)
                    isloadingPage.postValue(true)
                }
            }

        }

    }


}