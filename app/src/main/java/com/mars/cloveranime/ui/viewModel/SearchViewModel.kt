package com.mars.cloveranime.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.domain.FlvSearchUseCase
import com.mars.cloveranime.domain.SearchUseCase
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    val animesModel = MutableLiveData<MutableList<SearchAnimeModel>?>()
    val isloading = MutableLiveData<Boolean>()
    val searchUseCase = SearchUseCase()
    val flvSearchUseCase = FlvSearchUseCase()

    fun addListAnimes(query: String, provider: String){
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    isloading.postValue(true)
                    val animes: MutableList<SearchAnimeModel>? = searchUseCase(query)
                    animesModel.postValue(animes)
                    isloading.postValue(false)
                }
                "AnimeFLV" -> {
                    isloading.postValue(true)
                    val animes: MutableList<SearchAnimeModel>? = flvSearchUseCase(query)
                    animesModel.postValue(animes)
                    isloading.postValue(false)
                }
            }

        }

    }
}