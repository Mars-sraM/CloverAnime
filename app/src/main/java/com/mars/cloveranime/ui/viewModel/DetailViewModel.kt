package com.mars.cloveranime.ui.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.model.DetailAnimeModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.DetailImageModel
import com.mars.cloveranime.data.network.VideoEpisodesRequest
import com.mars.cloveranime.domain.DetailUseCase
import com.mars.cloveranime.domain.FlvDetailUseCase
import com.mars.cloveranime.domain.FlvViedeoServerUseCase
import com.mars.cloveranime.domain.VideoServerUseCase
import kotlinx.coroutines.launch


class DetailViewModel: ViewModel() {

    val detailModel = MutableLiveData<DetailAnimeModel?>()
    val detailImageModel = MutableLiveData<DetailImageModel?>()
    val detailModelPart2 = MutableLiveData<DetailAnimeModelPart2?>()
    val episodesVideoModel = MutableLiveData<String>()
    val sizeServersVideoModel = MutableLiveData<Int>()
    val isloading = MutableLiveData<Boolean>()

    val detailUseCase = DetailUseCase()
    val detailFlvUseCase = FlvDetailUseCase()
    val videoServerUseCase = VideoServerUseCase()
    val flvVideoServerUseCase = FlvViedeoServerUseCase()
    val network = VideoEpisodesRequest()

    fun addDetailImages(url: String, provider: String){
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    isloading.postValue(false)
                    val animeDetail = detailUseCase.detailImageUseCase(url)
                    detailImageModel.postValue(animeDetail)
                    isloading.postValue(true)
                }
                "AnimeFLV" -> {
                    isloading.postValue(false)
                    val animeDetail = detailFlvUseCase.detailImageUseCase(url)
                    detailImageModel.postValue(animeDetail)
                    isloading.postValue(true)
                }
            }

        }
    }
    fun addDetail(url: String, provider: String){
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    val animeDetail = detailUseCase.detailPart1UseCase(url)
                    detailModel.postValue(animeDetail)
                }
                "AnimeFLV" -> {
                    val animeDetail = detailFlvUseCase.detailPart1UseCase(url)
                    detailModel.postValue(animeDetail)
                }
            }
        }
    }
    fun addDetailPart2(url: String, provider: String){
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    val animeDetail = detailUseCase(url)
                    detailModelPart2.postValue(animeDetail)
                }
                "AnimeFLV" -> {
                    val animeDetail = detailFlvUseCase(url)
                    detailModelPart2.postValue(animeDetail)
                }
            }

        }
    }

    fun addVideosEpisodes(url: String, index: Int, provider: String) {
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    val videos = videoServerUseCase(url, index)
                    episodesVideoModel.postValue(videos)
                }
                "AnimeFLV" -> {
                    val videos = flvVideoServerUseCase.animeServer(url, index)
                    episodesVideoModel.postValue(videos)
                }
            }

        }
    }
    fun addSizeServers(url: String, provider: String) {
        viewModelScope.launch {
            when(provider){
                "MonosChinos" -> {
                    val videos = network.serverSize(url)
                    sizeServersVideoModel.postValue(videos)
                }
                "AnimeFLV" -> {
                    val videos = flvVideoServerUseCase.serverSize(url)
                    sizeServersVideoModel.postValue(videos)
                }
            }

        }
    }
}