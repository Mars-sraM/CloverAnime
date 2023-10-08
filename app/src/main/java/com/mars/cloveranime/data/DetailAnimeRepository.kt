package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.DetailAnimeModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.DetailImageModel
import com.mars.cloveranime.data.network.DetailAnimeRequest
import com.mars.cloveranime.data.network.VideoEpisodesRequest

class DetailAnimeRepository {
    val jsoupResponse = DetailAnimeRequest()
    val jsoupVideoResponse = VideoEpisodesRequest()

    suspend fun getAnimesImages(url:String): DetailImageModel {
        val result = jsoupResponse.requestDeatilImage(url)
        return result
    }
    suspend fun getDetailAnimesPart1(url:String): DetailAnimeModel {
        val result = jsoupResponse.requestDeatilAnime(url)
        return result
    }
    suspend fun getDetailAnimesPart2(url:String): DetailAnimeModelPart2 {
        val result = jsoupResponse.requestDeatilAnimePart2(url)
        return result
    }

    suspend fun getAnimesVideos(url:String, index: Int): String {
        val result = jsoupVideoResponse.requesvideoEpisodes(url, index)
        return result
    }
}