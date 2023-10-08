package com.mars.cloveranime.domain

import android.content.Context
import android.widget.Toast
import com.mars.cloveranime.data.FavoritesRepository
import com.mars.cloveranime.data.model.AnimeFavorite
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: FavoritesRepository){

    suspend operator fun invoke(context: Context): List<AnimeFavorite>{
        val animes = repository.getAnimeFavorite()

         if (animes.isEmpty()){
             return emptyList()
         } else {
             return animes
         }
    }
}