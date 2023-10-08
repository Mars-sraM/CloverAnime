package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException

class NewsAnimeRequest {

    private var newsAnimeList: MutableList<NewsModel> = mutableListOf()

    private fun newsAnime(): MutableList<NewsModel>{
        try {
            val kurl = "https://somoskudasai.com/categoria/noticias/anime/"
            val kdoc = Jsoup.connect(kurl).header("Cache-Control", "no-cache").get()
            kdoc.getElementsByClass("dn").remove()
            kdoc.getElementsByClass("typ ttu fwb fz2 white-bg primary-co brd1 dib pdx1 mab").remove()
            var elementsK: Elements = kdoc.select("main")
            for (i in 0 until 24) {
                val imgUrl = elementsK.select("img")
                    .eq(i)
                    .attr("src")
                val newTitle = elementsK.select("h2")
                    .eq(i)
                    .text()
                val newsFecha = elementsK.select("span")
                    .eq(i)
                    .text()
                val newsUrl = elementsK.select("a")
                    .eq(i)
                    .attr("href")

                //Log.i("Kudasai", "img: $imgUrl, title: $newTitle, Fecha: $newsUrl")

                newsAnimeList.add(NewsModel(newTitle, imgUrl, newsFecha, newsUrl))
            }

        } catch (e: IOException) {
            print(e)
        }
        return newsAnimeList
    }
    suspend fun requestAnimeNews(): MutableList<NewsModel>{
        return withContext(Dispatchers.IO) {
            val news = newsAnime()
            news
        }
    }
}