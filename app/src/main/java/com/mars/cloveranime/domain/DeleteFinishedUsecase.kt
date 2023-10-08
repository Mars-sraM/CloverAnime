package com.mars.cloveranime.domain

import com.mars.cloveranime.data.AnimesFinishedRepository
import javax.inject.Inject

class DeleteFinishedUsecase @Inject constructor(private val repository: AnimesFinishedRepository) {
    suspend fun deleteAnime(id: String) {
        repository.deleteAnime(id)
    }
    suspend fun deleteAllAnime() {
        repository.clearAnimes()
    }
}