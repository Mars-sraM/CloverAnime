package com.mars.cloveranime.data.network

import com.mars.cloveranime.data.model.DetailAnimeModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.DetailGenres
import com.mars.cloveranime.data.model.DetailImageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FlvDetailAnimeRequest {
    private var animeDetail = DetailAnimeModel("",
        emptyList<DetailGenres>().toMutableList(), "", "", "")
    private var animeDetailPart2 = DetailAnimeModelPart2("", "", "", "")
    private var animeDetailImage = DetailImageModel("")
    private var genres: MutableList<DetailGenres> = mutableListOf()

    fun detailImages(url: String): DetailImageModel {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get()
            val img = doc.getElementsByClass("Image").select("img").attr("src")
            animeDetailImage = DetailImageModel("https://www3.animeflv.net$img")

            //Log.i("data", "img: ${""}, title: $, animeUrl: ${animeDetailImage.detailimgPerfil}")

        } catch (e: HttpStatusException) {
            val statusCode: Int = e.statusCode
            if (statusCode == 404) {
                println("La URL no existe: $url")
                // Muestra un mensaje personalizado para el error 404
            }
        }

        return animeDetailImage
    }

    fun detailAnimes(url: String): DetailAnimeModel {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get()
            val genresElements = doc.getElementsByClass("Nvgnrs").select("a")
            genres = mutableListOf()
            for (i in 0 until genresElements.size) {
                val genresReques = genresElements.eq(i).text()
                genres.add(DetailGenres(genresReques))
            }

            val type = doc.getElementsByClass("Type tv").text()
            val status = doc.getElementsByClass("fa-tv").text()
            val synopsis = doc.getElementsByClass("Description")
                .select("p").text()
            val score = doc.getElementsByClass("vtprmd").text()

            animeDetail = DetailAnimeModel(type, genres, status, synopsis, score)


        } catch (e: HttpStatusException) {
            val statusCode: Int = e.statusCode
            if (statusCode == 404) {
                println("La URL no existe: $url")
                // Muestra un mensaje personalizado para el error 404
            }
        }
        return animeDetail
    }

    fun detailAnimePart2(url: String): DetailAnimeModelPart2 {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get()

            val id = doc.getElementsByClass("Strs RateIt").attr("data-id")
            val imageUrl = if (existUrl("https://www3.animeflv.net/uploads/animes/thumbs/$id.jpg")){
                "https://www3.animeflv.net/uploads/animes/thumbs/$id.jpg"
            } else{
                "https://www3.animeflv.net/uploads/animes/banners/$id.jpg"
            }
            val title = doc.select("div.Container > h1.Title").text()
            val date = doc.getElementsByClass("TxtAlt").eq(0).text()

            animeDetailPart2 = DetailAnimeModelPart2(title,imageUrl, date, url)

            //Log.i("data", "img: ${""}, title: $title, animeUrl: $url")

        } catch (e: HttpStatusException) {
            val statusCode: Int = e.statusCode
            if (statusCode == 404) {
                println("La URL no existe: $url")
                // Muestra un mensaje personalizado para el error 404
            }
        }

        return animeDetailPart2
    }
    fun detailAnimeModalDialog(url: String): DetailAnimeModelPart2 {
        try {
            val doc = Jsoup.connect(url).userAgent("Mozilla/5.0")
                .header("Cache-Control", "no-cache").get()
            val imageUrl = doc.getElementsByClass("Image").select("img").attr("src")
            val title = doc.select("div.Container > h1.Title").text()
            val date = doc.getElementsByClass("Description")
                .select("p").text()

            animeDetailPart2 = DetailAnimeModelPart2(
                title,
                "https://www3.animeflv.net$imageUrl",
                date,
                url)

            //Log.i("data", "img: ${""}, title: $title, animeUrl: $url")

        } catch (e: HttpStatusException) {
            val statusCode: Int = e.statusCode
            if (statusCode == 404) {
                println("La URL no existe: $url")
                // Muestra un mensaje personalizado para el error 404
            }
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