package com.mars.cloveranime.domain

import com.mars.cloveranime.data.FlvEpisodesRepository
import com.mars.cloveranime.data.model.Episodes


class FlvEpisodesUseCase {
    val repository = FlvEpisodesRepository()

    suspend fun episodesPage1UseCase(url: String): MutableList<Episodes>? =
        repository.getAnimesEpisodesPage1(url)

}