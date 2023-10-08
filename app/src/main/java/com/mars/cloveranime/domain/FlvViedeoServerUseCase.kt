package com.mars.cloveranime.domain

import com.mars.cloveranime.data.DetailFlvRepository

class FlvViedeoServerUseCase {
    val repository = DetailFlvRepository()
    suspend  fun animeServer(url: String, index: Int): String = repository.getAnimesVideos(url, index)
    suspend  fun serverSize(url: String): Int = repository.getServerSize(url)
}