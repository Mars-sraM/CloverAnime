package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.SearchAnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class McDirectoryAnimeRequest {
    private var resultList = mutableListOf<SearchAnimeModel>()
    private fun getAnimes(category: String, genero: String, page: Int): MutableList<SearchAnimeModel>{
        resultList = emptyList<SearchAnimeModel>().toMutableList()
        try {
            val url = "https://monoschinos2.com/animes?categoria=$category&genero=$genero&fecha=false&letra=false&p=$page"
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val elements: Elements = doc.select("div.seriesimg")

            for (i in 0 until elements.size){
                val animeUrl = doc.getElementsByClass("col-md-4 col-lg-2 col-6")
                    .select("a").eq(i).attr("href")
                val imageUrl = elements.select("img").eq(i).attr("data-src")
                val title = doc.getElementsByClass("seriesdetails")
                    .select("h3.seristitles").eq(i).text()
                val type = doc.getElementsByClass("seriesinfo").eq(i).text()
                //Log.i("data", "img: $imageUrl, title: $title, animeUrl: $animeUrl")
                resultList.add(SearchAnimeModel(title,imageUrl,type,animeUrl))
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