package com.mars.cloveranime.domain

import com.mars.cloveranime.data.AnimesFinishedRepository
import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity
import javax.inject.Inject

class AddFinishedUsecase @Inject constructor(private val repository: AnimesFinishedRepository) {
    suspend fun addAnime(anime: AnimeFinishedEntity) {
        repository.addAnimesFinished(anime)
    }
    suspend fun addAllAnime(anime: List<AnimeFinishedEntity>) {
        repository.clearAnimes()
        repository.addAllAnimeFinished(anime)
    }
}