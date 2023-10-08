package com.mars.cloveranime.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class VideoEpisodesRequest {
    var idVideos: String = ""
    var serverSize: Int = 0
    lateinit var elements: Elements
    fun videoEpisodes(url: String, index: Int): String {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            elements = doc.select("div.playother")

            for (i in 0 until elements.size) {
                idVideos = getEpisodes("p.play-video", index, "data-player")

                //Log.i("data", "img: $idVideos")

            }

        } catch (e: IOException) {
            print(e)
        }
        return idVideos
    }

    fun size(url: String): Int {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            elements = doc.select("div.playother")
            serverSize = elements.select("p.play-video").size
            Log.i("data", "img: $idVideos")
        } catch (e: IOException) {
            print(e)
        }
        return serverSize
    }

    private fun getEpisodes(query: String, index: Int, attributekey: String): String {
        val video = elements.select(query)
            .eq(index)
            .attr(attributekey)
        return video
    }

    suspend fun requesvideoEpisodes(url: String, index: Int): String {
        return withContext(Dispatchers.IO) {
            val videos = videoEpisodes(url, index)
            videos
        }
    }
    suspend fun serverSize(url: String): Int{
        return withContext(Dispatchers.IO) {
            val size = size(url)
            size
        }
    }
}