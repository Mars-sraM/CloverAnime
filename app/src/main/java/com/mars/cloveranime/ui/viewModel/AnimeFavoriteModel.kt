package com.mars.cloveranime.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.database.entities.AnimeEntity
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.domain.AddFavoritesUseCase
import com.mars.cloveranime.domain.DeleteFavoriteUseCase
import com.mars.cloveranime.domain.GetFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class AnimeFavoriteModel @Inject constructor(
    private val adduseCase: AddFavoritesUseCase,
    private val getuseCase: GetFavoritesUseCase,
    private val deleteuseCase: DeleteFavoriteUseCase
): ViewModel() {
    val animesFavorites = MutableLiveData <List<AnimeFavorite>>()
    fun getAnimes(contex: Context) {
        viewModelScope.launch {
            val listAnime = getuseCase(contex)
            Collections.reverse(listAnime)
            animesFavorites.postValue(listAnime)
        }
    }
    fun addAnimes(anime: AnimeEntity){
        viewModelScope.launch { adduseCase.addAnime(anime) }
    }
    fun addAllAnimes(anime: List<AnimeEntity>){
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