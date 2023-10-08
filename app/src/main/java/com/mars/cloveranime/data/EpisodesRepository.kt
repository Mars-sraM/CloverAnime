package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.Episodes
import com.mars.cloveranime.data.network.EpisodesAnimeRequest

class EpisodesRepository {
    val jsoupResponse = EpisodesAnimeRequest()

    suspend fun getAnimesEpisodesPage1(url:String): MutableList<Episodes> {
        val result = jsoupResponse.requestEpisodes(url)
        return result
    }
}