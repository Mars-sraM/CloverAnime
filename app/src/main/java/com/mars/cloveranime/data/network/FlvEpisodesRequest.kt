package com.mars.cloveranime.data.network

import android.util.Log
import com.google.gson.JsonParser
import com.mars.cloveranime.data.model.Episodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException
import kotlin.properties.Delegates


class FlvEpisodesRequest {
    private var episodes = mutableListOf<Episodes>()
    private var sizeFull = 0

    fun episodes(url: String): MutableList<Episodes> {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val script: Element? = doc.select("script:containsData(episodes)").first()
            val price: String = script?.data()?.split("var episodes = ")?.get(1)!!
                .split("var last_seen = ").get(0).replace(";", "")
            val jsonParser = JsonParser()
            val elementsEpisodes = jsonParser.parse(price).asJsonArray
            sizeFull = elementsEpisodes.size()
            episodes = mutableListOf()
            val img = doc.getElementsByClass("Image").select("img").attr("src")
            val title = doc.select("div.Container > h1.Title").text()
            val id = url.replace("https://www3.animeflv.net/anime/", "")
            for (i in 0 until sizeFull) {
                val x = elementsEpisodes[i].asJsonArray.get(0).asString
                val imageUrl = "https://www3.animeflv.net$img"
                val noCapitulos = "Episodio $x"
                val name = title
                val episodeUrl = "https://www3.animeflv.net/ver/$id-$x"

                episodes.add(Episodes(name, noCapitulos, episodeUrl, imageUrl))
               // Log.i("data", "img: $imageUrl, title: $noCapitulos, animeUrl: $episodeUrl")
            }
        } catch (e: IOException) {
            print(e)
        }
        return episodes
    }

    suspend fun requestEpisodes(url: String): MutableList<Episodes> {
        return withContext(Dispatchers.IO) {
            val episodes = episodes(url)
            episodes
        }
    }
}