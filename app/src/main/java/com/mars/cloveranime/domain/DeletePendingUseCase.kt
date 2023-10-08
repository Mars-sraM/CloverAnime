package com.mars.cloveranime.domain

import com.mars.cloveranime.data.AnimesPendingRepository
import javax.inject.Inject

class DeletePendingUseCase @Inject constructor(private val repository: AnimesPendingRepository) {

    suspend fun deleteAnime(id: String) {
        repository.deleteAnimePending(id)
    }
    suspend fun deleteAllAnime() {
        repository.clearAnimesPending()
    }
}