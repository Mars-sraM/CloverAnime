package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.SearchAnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class FlvDirectoryAnimeRequest {
    private var resultList = mutableListOf<SearchAnimeModel>()
    private fun getAnimes(category: String, genero: String, page: Int): MutableList<SearchAnimeModel>{
        resultList = emptyList<SearchAnimeModel>().toMutableList()
        try {
            //genre%5B%5D=accion&
            val url = "https://www3.animeflv.net/browse?${genero}type%5B%5D=$category&order=added&page=$page"
            println("la url es: $url")
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val elements: Elements = doc.getElementsByClass("ListAnimes AX Rows A03 C02 D02")

            for (i in 0 until elements.select("li").size){
                val animeUrl = doc.getElementsByClass("Anime alt B")
                    .select("a").eq(i).attr("href")
                val imageUrl = doc.getElementsByClass("Image fa-play-circle-o")
                    .select("img").eq(i).attr("src")
                val title = doc.getElementsByClass("Anime alt B")
                    .select("h3.Title").eq(i).text()
                val type = doc.getElementsByClass("Image fa-play-circle-o")
                    .select("span").eq(i).text()
                //Log.i("data", "img: $imageUrl, title: $title, animeUrl: $animeUrl")
                resultList.add(SearchAnimeModel(title,imageUrl,type,"https://www3.animeflv.net$animeUrl"))
            }

        }
        catch (e: IOException) {
            print(e)
        }
        return resultList
    }
    suspend fun resulAnime(category: String, genero: String, page: Int): MutableList<SearchAnimeModel>{
        return withContext(Dispatchers.IO){
            val animes = getAnimes(category, genero, page)
            animes
        }
    }
}