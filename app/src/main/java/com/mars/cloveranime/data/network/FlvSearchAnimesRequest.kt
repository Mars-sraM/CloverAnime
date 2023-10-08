package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.SearchAnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class FlvSearchAnimesRequest {
    private var resultList = mutableListOf<SearchAnimeModel>()
    private var finalList = mutableListOf<SearchAnimeModel>()
    private fun searchAnimes(query: String): MutableList<SearchAnimeModel>{
        resultList = emptyList<SearchAnimeModel>().toMutableList()
        finalList = emptyList<SearchAnimeModel>().toMutableList()
        try {
            val url = "https://www3.animeflv.net/browse?q=$query"
            val baseUrl = "https://www3.animeflv.net"
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val elements: Elements = doc.getElementsByClass("ListAnimes AX Rows A03 C02 D02")
                .select("li")

            for (i in 0 until elements.size){
                val animeUrl = baseUrl + doc.getElementsByClass("Description")
                    .select("a").eq(i).attr("href")
                val imageUrl = doc.getElementsByClass("Image fa-play-circle-o")
                    .select("img").eq(i).attr("src")
                val title = elements.select("h3.Title").eq(i).text()
                val type = doc.getElementsByClass("Type tv").eq(i).text()
                //Log.i("data", "img: $imageUrl, title: $title, animeUrl: $animeUrl")
                resultList.add(SearchAnimeModel(title,imageUrl,type,animeUrl))
            }
            finalList.addAll(resultList)
        }
        catch (e: IOException) {
            print(e)
        }
        return finalList
    }
    suspend fun resultSearchAnime(query: String): MutableList<SearchAnimeModel>{
        return withContext(Dispatchers.IO){
            val animes = searchAnimes(query)
            animes
        }
    }
}