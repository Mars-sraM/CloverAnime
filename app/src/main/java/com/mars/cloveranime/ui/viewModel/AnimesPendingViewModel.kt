package com.mars.cloveranime.ui.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.domain.AddPendingUseCase
import com.mars.cloveranime.domain.DeletePendingUseCase
import com.mars.cloveranime.domain.GetPendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Collections
import javax.inject.Inject

@HiltViewModel
class AnimesPendingViewModel @Inject constructor(
    private val adduseCase: AddPendingUseCase,
    private val getuseCase: GetPendingUseCase,
    private val deleteuseCase: DeletePendingUseCase
): ViewModel() {

    val animesPending = MutableLiveData <List<AnimeFavorite>>()
    fun getAnimes(contex: Context) {
        viewModelScope.launch {
            val listAnime = getuseCase(contex)
            Collections.reverse(listAnime)
            animesPending.postValue(listAnime)
        }
    }
    fun addAnimes(anime: AnimesPendingEntity){
        viewModelScope.launch { adduseCase.addAnime(anime) }
    }
    fun addAllAnimes(anime: List<AnimesPendingEntity>){
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