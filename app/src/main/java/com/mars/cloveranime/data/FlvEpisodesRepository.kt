package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.Episodes
import com.mars.cloveranime.data.network.FlvEpisodesRequest

class FlvEpisodesRepository {
    val jsoupResponse = FlvEpisodesRequest()

    suspend fun getAnimesEpisodesPage1(url:String): MutableList<Episodes> {
        val result = jsoupResponse.requestEpisodes(url)
        return result
    }

}