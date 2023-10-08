package com.mars.cloveranime.domain

import com.mars.cloveranime.data.AnimesPendingRepository
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity
import javax.inject.Inject

class AddPendingUseCase @Inject constructor(private val repository:AnimesPendingRepository) {
    /*suspend operator fun invoke(anime: AnimesPendingEntity){
        repository.addAnimePending(anime)
    }
     */
    suspend fun addAnime(anime: AnimesPendingEntity) {
        repository.addAnimePending(anime)
    }
    suspend fun addAllAnime(anime: List<AnimesPendingEntity>) {
        repository.clearAnimesPending()
        repository.addAllAnimePending(anime)
    }
}