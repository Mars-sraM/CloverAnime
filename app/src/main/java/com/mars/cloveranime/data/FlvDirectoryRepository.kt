package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.data.network.FlvDirectoryAnimeRequest

class FlvDirectoryRepository {
    val jsoupResponse = FlvDirectoryAnimeRequest()

    suspend fun getAllAnimes(category: String, genero: String, page: Int): MutableList<SearchAnimeModel>{
        val result = jsoupResponse.resulAnime(category, genero, page)
        return result
    }
}