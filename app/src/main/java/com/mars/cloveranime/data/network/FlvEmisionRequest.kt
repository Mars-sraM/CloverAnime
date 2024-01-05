package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.AnimeUrl
import com.mars.cloveranime.data.model.CapEmisionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class FlvEmisionRequest {
    var list = mutableListOf<CapEmisionModel>()
    fun capEmision():MutableList<CapEmisionModel> {
        try {
            val url = "https://www3.animeflv.net"
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val elements: Elements = doc.getElementsByClass("ListEpisodios AX Rows A06 C04 D03")
            list = emptyList<CapEmisionModel>().toMutableList()
            for (i in 0 until elements.select("li").size) {
                val capUrl = url + elements.select("a").eq(i).attr("href")
                val imgUrl = url + elements.select("img").eq(i).attr("src")

                val title = elements.select("strong.Title").eq(i).text()
                val capitulo = doc.getElementsByClass("Capi").eq(i).text()
                val capituloNo = capitulo.replace("Episodio ", "")
                val animeType = "Anime"
                val url1 = capUrl.replace("/ver/", "/anime/")
                val animeUrl = url1.replace("-$capituloNo", "")

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
