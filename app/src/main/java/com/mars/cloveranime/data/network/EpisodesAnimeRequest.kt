package com.mars.cloveranime.data.network

import com.mars.cloveranime.data.model.Episodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.properties.Delegates

class EpisodesAnimeRequest {

    private var episodes = mutableListOf<Episodes>()
    var size = 0

    fun episodes(url: String): MutableList<Episodes> {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val elementsEpisodes: Elements = doc.select("div.heroarea2")
            if (elementsEpisodes.select("div.col-item").size < 200){
                size = elementsEpisodes.select("div.col-item").size
                val img =  doc.select("div.animeimgdiv")
                    .select("img").eq(0).attr("data-src")
                val animeName = doc.select("h1.mobh1").eq(0).text()
                val epiUrl = url.replace("anime","ver")

                episodes = mutableListOf()
                for (i in 0 until size) {
                    val noCapitulos = doc.select("p.animetitles").eq(i).text()
                    val xnoCapitulos = noCapitulos.replace("Capitulo ", "")
                    val finalnoCap = xnoCapitulos.replace(" ", "")
                    val episodeUrl = epiUrl.replace("sub-espanol","episodio-$finalnoCap")

                    episodes.add(Episodes(animeName, noCapitulos, episodeUrl, img))
                }
            }else{
                size = elementsEpisodes.select("div.col-item").size - 1
                val sizeEpisodes = doc.getElementsByClass("col-item").eq(size).attr("data-episode")
                val img =  doc.select("div.animeimgdiv")
                    .select("img").eq(0).attr("data-src")
                val animeName = doc.select("h1.mobh1").eq(0).text()
                val epiUrl = url.replace("anime","ver")
                val episodeUrl = epiUrl.replace("sub-espanol","episodio-")
                val init = if (existUrl(episodeUrl + "0")){ 0 }else{ 1 }
                episodes = mutableListOf()
                for (i in init until sizeEpisodes.toInt() + 1) {
                    val noCapitulos = "Capitulo $i"
                    val capUrl = "$episodeUrl$i"
                    episodes.add(Episodes(animeName, noCapitulos, capUrl, img))
                }
            }

        } catch (e: IOException) {
            print(e)
        }
        return episodes
    }

    private fun existUrl(url: String): Boolean {
        var urlExists = true
        val huc = URL(url).openConnection() as HttpURLConnection
        val responseCode: Int = huc.responseCode
        if (responseCode != HttpURLConnection.HTTP_OK) {
            urlExists = false
        }
        return urlExists
    }
    suspend fun requestEpisodes(url: String): MutableList<Episodes> {
        return withContext(Dispatchers.IO) {
            val episodes = episodes(url)
            episodes
        }
    }
}