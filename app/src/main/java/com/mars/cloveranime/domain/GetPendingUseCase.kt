package com.mars.cloveranime.domain

import android.content.Context
import android.widget.Toast
import com.mars.cloveranime.data.AnimesPendingRepository
import com.mars.cloveranime.data.model.AnimeFavorite
import javax.inject.Inject

class GetPendingUseCase @Inject constructor(private val repository: AnimesPendingRepository) {

    suspend operator fun invoke(context: Context): List<AnimeFavorite>{
        val animes = repository.getAnimesPending()

        if (animes.isEmpty()){
            return emptyList()
        } else {
            return animes
        }
    }
}