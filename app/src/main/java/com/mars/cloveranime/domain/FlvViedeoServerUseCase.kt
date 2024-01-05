package com.mars.cloveranime.domain

import com.mars.cloveranime.data.DetailFlvRepository
import com.mars.cloveranime.data.model.AnimeVideoServer

class FlvViedeoServerUseCase {
    val repository = DetailFlvRepository()
    suspend  fun animeServer(url: String): AnimeVideoServer = repository.getAnimesVideos(url)
}