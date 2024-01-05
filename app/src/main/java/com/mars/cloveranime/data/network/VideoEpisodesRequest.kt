package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.AnimeVideoServer
import com.mars.cloveranime.data.model.Server
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class VideoEpisodesRequest {
    var idVideos: String = ""
    var serverSize: Int = 0
    private var listServer = mutableListOf<Server>()
    lateinit var elements: Elements
    fun videoEpisodes(url: String): AnimeVideoServer {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            elements = doc.select("div.playother")
            serverSize = elements.select("p.play-video").size
            listServer = emptyList<Server>().toMutableList()
            for (i in 0 until serverSize) {
                val video = elements.select("p.play-video")
                    .eq(i)
                    .attr("data-player")
                val titleServer = elements.select("p.play-video")
                    .eq(i)
                    .text()
                listServer.add(Server(
                    titleServer,
                    "https://monoschinos2.com/reproductor?url=$video")
                )
                //Log.i("data", "img: $idVideos")
            }

        } catch (e: IOException) {
            print(e)
        }
        return AnimeVideoServer(serverSize, listServer)
    }

    suspend fun requesvideoEpisodes(url: String): AnimeVideoServer {
        return withContext(Dispatchers.IO) {
            val videos = videoEpisodes(url)
            videos
        }
    }
}