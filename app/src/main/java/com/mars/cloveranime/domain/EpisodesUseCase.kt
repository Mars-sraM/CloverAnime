package com.mars.cloveranime.domain

import com.mars.cloveranime.data.DetailAnimeRepository
import com.mars.cloveranime.data.EpisodesRepository
import com.mars.cloveranime.data.model.DetailAnimeModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.Episodes

class EpisodesUseCase {
    val repository = EpisodesRepository()
    suspend fun episodesPage1UseCase(url: String): MutableList<Episodes>? =
        repository.getAnimesEpisodesPage1(url)
}