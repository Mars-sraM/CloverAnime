package com.mars.cloveranime.domain

import com.mars.cloveranime.data.DetailAnimeRepository
import com.mars.cloveranime.data.model.AnimeVideoServer

class VideoServerUseCase {
    val repository = DetailAnimeRepository()
    suspend operator fun invoke(url: String): AnimeVideoServer = repository.getAnimesVideos(url)
}