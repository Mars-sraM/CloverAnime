package com.mars.cloveranime.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.model.Episodes
import com.mars.cloveranime.data.network.EpisodesAnimeRequest
import com.mars.cloveranime.domain.EpisodesUseCase
import com.mars.cloveranime.domain.FlvEpisodesUseCase
import kotlinx.coroutines.launch

class EpisodesViewModel: ViewModel() {

    val episodeslModel = MutableLiveData<MutableList<Episodes>?>()
    val isloading = MutableLiveData<Boolean>()
    val boolean = EpisodesAnimeRequest()

    val episodesUseCase = EpisodesUseCase()
    val flvEpisodesUseCase = FlvEpisodesUseCase()

    fun addEpisodesPage1(url: String, provider: String){
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    isloading.postValue(false)
                    val animeDetail = episodesUseCase.episodesPage1UseCase(url)
                    episodeslModel.postValue(animeDetail)
                    isloading.postValue(true)
                }
                "AnimeFLV" -> {
                    isloading.postValue(false)
                    val animeDetail = flvEpisodesUseCase.episodesPage1UseCase(url)
                    episodeslModel.postValue(animeDetail)
                    isloading.postValue(true)
                }
            }
        }
    }
}