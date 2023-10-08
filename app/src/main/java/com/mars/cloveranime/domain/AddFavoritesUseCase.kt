package com.mars.cloveranime.domain

import com.mars.cloveranime.data.FavoritesRepository
import com.mars.cloveranime.data.database.entities.AnimeEntity
import javax.inject.Inject

class AddFavoritesUseCase @Inject constructor(private val repository: FavoritesRepository) {
    /*suspend operator fun invoke(anime: AnimeEntity){
       repository.addAnimesFavorite(anime)
    }
     */
    suspend fun addAnime(anime: AnimeEntity) {
        repository.addAnimesFavorite(anime)
    }
    suspend fun addAllAnime(anime: List<AnimeEntity>) {
        repository.clearAnimes()
        repository.addAllAnimeFavorite(anime)
    }
}