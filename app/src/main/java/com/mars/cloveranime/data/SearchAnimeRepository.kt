package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.data.network.SearchAnimesResult

class SearchAnimeRepository {

    val jsoupResponse = SearchAnimesResult()

    suspend fun getAllAnimes(query:String): MutableList<SearchAnimeModel>{
        val result = jsoupResponse.resultSearchAnime(query)
        return result
    }
}