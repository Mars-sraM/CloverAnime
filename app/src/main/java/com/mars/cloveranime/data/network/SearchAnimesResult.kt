package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.SearchAnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class SearchAnimesResult {
    private var resultList = mutableListOf<SearchAnimeModel>()
    private var finalList = mutableListOf<SearchAnimeModel>()
    private fun searchAnimes(query: String): MutableList<SearchAnimeModel>{
        resultList = emptyList<SearchAnimeModel>().toMutableList()
        finalList = emptyList<SearchAnimeModel>().toMutableList()
        try {
            val url = "https://monoschinos2.com/buscar?q=$query"
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val elements: Elements = doc.select("div.row")

            for (i in 0 until elements.select("div.seriesimg").size){
                val animeUrl = elements.select("a").eq(i).attr("href")
                val imageUrl = elements.select("div.seriesimg")
                    .select("img").eq(i).attr("src")
                val title = elements.select("div.seriesdetails")
                    .select("h3.seristitles").eq(i).text()
                val type = elements.select("div.seriesdetails")
                    .select("span.seriesinfo").eq(i).text()
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