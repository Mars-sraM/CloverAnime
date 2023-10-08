package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.data.network.FlvSearchAnimesRequest

class FlvSearchAnimeRepository {
    val jsoupResponse = FlvSearchAnimesRequest()

    suspend fun getAllAnimes(query:String): MutableList<SearchAnimeModel>{
        val result = jsoupResponse.resultSearchAnime(query)
        return result
    }
}