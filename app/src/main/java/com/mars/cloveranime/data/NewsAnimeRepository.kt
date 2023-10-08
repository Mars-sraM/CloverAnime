package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.NewsModel
import com.mars.cloveranime.data.network.NewsAnimeRequest

class NewsAnimeRepository {
    val jsoupResponse = NewsAnimeRequest()
    suspend fun getNewsAnime(): MutableList<NewsModel> {
        val result = jsoupResponse.requestAnimeNews()
        return result
    }
}