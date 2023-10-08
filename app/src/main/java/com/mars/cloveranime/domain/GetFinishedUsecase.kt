package com.mars.cloveranime.domain

import android.content.Context
import android.widget.Toast
import com.mars.cloveranime.data.AnimesFinishedRepository
import com.mars.cloveranime.data.model.AnimeFavorite
import javax.inject.Inject

class GetFinishedUsecase @Inject constructor(private val repository: AnimesFinishedRepository) {
    suspend operator fun invoke(context: Context): List<AnimeFavorite>{
        val animes = repository.getAnimeFinished()

        if (animes.isEmpty()){
            return emptyList()
        } else {
            return animes
        }
    }
}