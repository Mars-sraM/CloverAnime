package com.mars.cloveranime.domain

import android.content.Context
import com.mars.cloveranime.data.FavoritesRepository
import com.mars.cloveranime.data.model.AnimeFavorite
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val repository: FavoritesRepository) {

    suspend fun deleteAnime(id: String) {
        repository.deleteAnime(id)
    }
    suspend fun deleteAllAnime() {
        repository.clearAnimes()
    }
}