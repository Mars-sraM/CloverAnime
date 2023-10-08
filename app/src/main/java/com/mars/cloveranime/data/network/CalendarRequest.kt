package com.mars.cloveranime.data.network

import android.content.ContentValues
import android.util.Log
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.data.model.SearchAnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.File
import java.io.IOException

class CalendarRequest {

    var list = mutableListOf<CalendarModel>()
    private fun getCalendar(day: Int):MutableList<CalendarModel> {
        try {
            val url = "https://monoschinos2.com/calendario"
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()

            val elements: Elements = doc.getElementsByClass("accordionItem close").eq(day)
            list = emptyList<CalendarModel>().toMutableList()
            for (i in 0 until elements.select("div.seriesimg").size) {
                val animeUrl =elements.select("div.seriesimg > a").eq(i).attr("href")
                val imgUrl = elements.select("div.seriesimg > a > img").eq(i).attr("data-src")
                val title = elements.select("div.serisdtls > a > h3").eq(i).text()
                val synapsis = elements.select("div.serisdtls > a > p").eq(i).text()
                val animeProvider = "MonosChinos"

                //Log.i("data", "img: $imgUrl, title: $title, animeUrl: $animeUrl")

                list.add(CalendarModel(title, imgUrl, animeUrl,synapsis, animeProvider))
            }

        } catch (e: IOException) {
            print(e)
        }
        return list
    }
    suspend fun requestCalendar(day: Int): MutableList<CalendarModel> {
        return withContext(Dispatchers.IO) {
            val episodes = getCalendar(day)
            episodes
        }
    }


}