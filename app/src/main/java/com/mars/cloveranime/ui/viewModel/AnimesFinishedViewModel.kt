package com.mars.cloveranime.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.domain.AddFinishedUsecase
import com.mars.cloveranime.domain.DeleteFinishedUsecase
import com.mars.cloveranime.domain.GetFinishedUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Collections

import javax.inject.Inject

@HiltViewModel
class AnimesFinishedViewModel @Inject constructor(
    private val adduseCase: AddFinishedUsecase,
    private val getuseCase: GetFinishedUsecase,
    private val deleteuseCase: DeleteFinishedUsecase
): ViewModel() {
    val animesFinished = MutableLiveData <List<AnimeFavorite>>()
    fun getAnimes(contex: Context) {
        viewModelScope.launch {
            val listAnime = getuseCase(contex)
            Collections.reverse(listAnime)
            animesFinished.postValue(listAnime)
        }
    }
    fun addAnimes(anime: AnimeFinishedEntity){
        viewModelScope.launch { adduseCase.addAnime(anime) }
    }
    fun addAllAnimes(anime: List<AnimeFinishedEntity>){
        Collections.reverse(anime)
        viewModelScope.launch { adduseCase.addAllAnime(anime) }
    }

    fun deleteAnime(id: String){
        viewModelScope.launch { deleteuseCase.deleteAnime(id) }
    }
    fun deleteAllAnime(){
        viewModelScope.launch { deleteuseCase.deleteAllAnime() }
    }
}