package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.AnimeUrl
import com.mars.cloveranime.data.model.CapEmisionModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class CapEmisionRequest {

    var list = mutableListOf<CapEmisionModel>()

    fun capEmision():MutableList<CapEmisionModel> {
        try {
            val url = "https://monoschinos2.com/"
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            list = mutableListOf()
            var elements: Elements = doc.select("div.animes")
            var elementsUrl: Elements = doc.select("div.heroarea")
            elementsUrl.select("div.container").remove()
            println(elements.select("a").size)
            for (i in 0 until elements.size) {
                val capUrl = elementsUrl.select("a").eq(i).attr("href")
                val imgUrl = elements.select("div.animeimgdiv")
                    .select("img")
                    .eq(i)
                    .attr("data-src")
                val title = elements.select("h2.animetitles")
                    .eq(i)
                    .text()
                val capituloNo = elements.select("div.animeimgdiv")
                    .select("div.hoverdiv")
                    .select("div.positioning")
                    .select("p")
                    .eq(i)
                    .text()
                val animeType = elements.select("div.animeimgdiv")
                    .select("div.hoverdiv")
                    .select("div.positioning")
                    .select("button")
                    .eq(i)
                    .text()
                val url1 = capUrl.replace("ver", "anime")
                val parts = url1.split("episodio");
                val animeUrl = parts[0] + "sub-espanol"
                //Log.i("data", "img: $imgUrl, title: $title, animeUrl: $animeUrl")

                list.add(CapEmisionModel(title, imgUrl, capituloNo, animeType, AnimeUrl( capUrl, animeUrl)))
            }

        } catch (e: IOException) {
            print(e)
        }
        return list
    }
    suspend fun requestCapEmision(): MutableList<CapEmisionModel> {
        return withContext(Dispatchers.IO) {
            val episodes = capEmision()
            episodes
        }
    }
}