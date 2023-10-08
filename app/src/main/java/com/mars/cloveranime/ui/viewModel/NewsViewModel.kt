package com.mars.cloveranime.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.model.NewsModel
import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.domain.NewsAnimeUseCase
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    val newsViewModel = MutableLiveData<MutableList<NewsModel>?>()
    val newsUseCase = NewsAnimeUseCase()
    val isloading = MutableLiveData<Boolean>()

    fun addListNews(){
        viewModelScope.launch {
            isloading.postValue(false)
            val animes: MutableList<NewsModel>? = newsUseCase()
            newsViewModel.postValue(animes)
            isloading.postValue(true)
        }
    }
}