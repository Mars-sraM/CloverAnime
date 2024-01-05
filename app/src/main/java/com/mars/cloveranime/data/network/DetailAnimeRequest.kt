package com.mars.cloveranime.data.network

import android.util.Log
import com.mars.cloveranime.data.model.DetailAnimeModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.DetailGenres
import com.mars.cloveranime.data.model.DetailImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class DetailAnimeRequest {
    private var animeDetail = DetailAnimeModel("",
        emptyList<DetailGenres>().toMutableList(), "", "", "")
    private var animeDetailPart2 = DetailAnimeModelPart2("", "", "", "")
    private var animeDetailImage = DetailImageModel("")
    private var genres: MutableList<DetailGenres> = mutableListOf()

    fun detailImages(url: String): DetailImageModel {
        try {
            val x = url.replace("https://monoschinos2.com/", "https://www3.animeflv.net/")
            val urlFLV = x.replace("-sub-espanol", "")
            animeDetailImage = if (existUrl(urlFLV)){
                val docFLV = Jsoup.connect(urlFLV).get()
                val imgUrlFLV = docFLV.getElementsByClass("Image")
                    .select("img").attr("src")
                DetailImageModel("https://www3.animeflv.net$imgUrlFLV")
            } else{
                val doc = Jsoup.connect(url).get()
                val imgUrl = doc.getElementsByClass("chapterpic")
                    .select("img").attr("src")
                DetailImageModel(imgUrl)
            }
            //Log.i("data", "img: ${""}, title: $, animeUrl: ${animeDetailImage.detailimgPerfil}")

        } catch (e: IOException) {
            print(e)
        }

        return animeDetailImage
    }

    fun detailAnimes(url: String): DetailAnimeModel {
        try {
            val doc = Jsoup.connect(url).get()
            val elements: Elements = doc.select("div.heroarea")
            val elementsEpisodes: Elements = doc.select("div.heroarea2")
            genres = mutableListOf()
            for (i in 0 until elements.select("div.heroarea").select("ol.breadcrumb").eq(0)
                .select("li.breadcrumb-item").size) {
                val genresReques = elements.select("div.chapterdetails")
                    .select("ol.breadcrumb").eq(0)
                    .select("li.breadcrumb-item").eq(i).text()
                genres.add(DetailGenres(genresReques))
            }

            val type = elements.select("div.chapterdetls2").select("tbody")
                .select("td").eq(5).text()
            val status = elements.select("div.chapterdetls2").select("tbody")
                .select("td").eq(3).text()
            val synopsis = elements.select("div.chapterdetls2").select("p").text()
            val score = elements.select("div.chapterpic").select("p").text()

            animeDetail = DetailAnimeModel(type, genres, status, synopsis, score)
            //Log.i("data", "img: $type, title: $status, animeUrl: $genres")
        } catch (e: IOException) {
            print(e)
        }
        return animeDetail
    }

    fun detailAnimePart2(url: String): DetailAnimeModelPart2 {
        try {
            val doc = Jsoup.connect(url).get()

            val elements: Elements = doc.select("div.heroarea")
            val imageUrl = doc.select("div.herobg").select("img").attr("src")
            val title = elements.select("h1.mobh1").text()
            val date = elements.select("div.chapterdetails")
                .select("ol.breadcrumb").eq(1)
                .select("li.breadcrumb-item").text()

            animeDetailPart2 = DetailAnimeModelPart2(title,imageUrl, date, url)
            //Log.i("data", "img: ${""}, title: $date, animeUrl: $url")
        } catch (e: IOException) {
            print(e)
        }

        return animeDetailPart2
    }
    fun detailAnimeModalDialog(url: String): DetailAnimeModelPart2 {
        try {
            val doc = Jsoup.connect(url).header("Cache-Control", "no-cache").get()

            val elements: Elements = doc.select("div.heroarea")
            val imageUrl = doc.getElementsByClass("chapterpic")
                .select("img").attr("src")
            val title = elements.select("h1.mobh1").text()
            val date = elements.select("div.chapterdetls2").select("p").text()

            animeDetailPart2 = DetailAnimeModelPart2(title,imageUrl, date, url)
            //Log.i("data", "img: ${""}, title: $date, animeUrl: $url")
        } catch (e: IOException) {
            print(e)
        }

        return animeDetailPart2
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

    suspend fun requestDeatilImage(url: String): DetailImageModel {
        return withContext(Dispatchers.IO) {
            val animes = detailImages(url)
            animes
        }
    }

    suspend fun requestDeatilAnime(url: String): DetailAnimeModel {
        return withContext(Dispatchers.IO) {
            val animes = detailAnimes(url)
            animes
        }
    }

    suspend fun requestDeatilAnimePart2(url: String): DetailAnimeModelPart2 {
        return withContext(Dispatchers.IO) {
            val animesPart2 = detailAnimePart2(url)
            animesPart2
        }
    }
    suspend fun requestDeatilAnimeModalDialog(url: String): DetailAnimeModelPart2 {
        return withContext(Dispatchers.IO) {
            val animesPart2 = detailAnimeModalDialog(url)
            animesPart2
        }
    }

}