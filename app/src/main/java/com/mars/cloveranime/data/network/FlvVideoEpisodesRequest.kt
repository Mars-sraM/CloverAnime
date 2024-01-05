package com.mars.cloveranime.data.network

import android.util.Log
import com.google.gson.JsonParser
import com.mars.cloveranime.data.model.AnimeVideoServer
import com.mars.cloveranime.data.model.Server
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException

class FlvVideoEpisodesRequest {
    var idVideos: String = ""
    var serverSize: Int = 0
    private var listServer = mutableListOf<Server>()
    fun videoEpisodes(url: String): AnimeVideoServer {
        listServer = emptyList<Server>().toMutableList()
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val script: Element? = doc.select("script:containsData(videos)").first()
            val price: String = script?.data()?.split("var videos = ")?.get(1)!!.replace(
                "" +
                        "//", ""
            ).replace(";", "")
            val jsonParser = JsonParser()
            val myJson = jsonParser.parse(price).asJsonObject.getAsJsonArray("SUB")
            serverSize = myJson.size()
            for (i in 0 until serverSize){
                idVideos = myJson.get(i).asJsonObject.get("code").asString
                val serverName = myJson.get(i).asJsonObject.get("title").asString
                listServer.add(Server(serverName, idVideos))
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
