package com.mars.cloveranime.data.network

import android.util.Log
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException

class FlvVideoEpisodesRequest {
    var idVideos: String = ""
    var serverSize: Int = 0
    fun videoEpisodes(url: String, index: Int): String {
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
            idVideos = myJson.get(index).asJsonObject.get("code").asString

        } catch (e: IOException) {
            print(e)
        }
        return idVideos
    }
    fun size(url: String): Int {
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
        } catch (e: IOException) {
            print(e)
        }
        return serverSize
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
