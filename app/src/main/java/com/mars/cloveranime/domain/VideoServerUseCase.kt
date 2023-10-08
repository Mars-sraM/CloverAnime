package com.mars.cloveranime.domain

import com.mars.cloveranime.data.DetailAnimeRepository
import com.mars.cloveranime.data.model.Episodes

class VideoServerUseCase {
    val repository = DetailAnimeRepository()
    suspend operator fun invoke(url: String, index: Int): String = repository.getAnimesVideos(url, index)
}