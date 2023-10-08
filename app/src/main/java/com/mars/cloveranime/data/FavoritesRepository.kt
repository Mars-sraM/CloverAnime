package com.mars.cloveranime.data

import com.mars.cloveranime.data.database.entities.AnimeEntity
import com.mars.cloveranime.data.database.entities.dao.AnimeDao
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.data.model.toDomain
import java.util.Collections
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoriteDao: AnimeDao
) {

    suspend fun getAnimeFavorite(): List<AnimeFavorite> {
        val response = favoriteDao.getListFavorites()

        return response.map { it.toDomain() }
    }
    suspend fun addAnimesFavorite(anime: AnimeEntity) {
        favoriteDao.inserAnime(anime)
    }
    suspend fun addAllAnimeFavorite(listAnime: List<AnimeEntity>) {

        favoriteDao.inserAllAnimeList(listAnime)
    }
    suspend fun clearAnimes(){
        favoriteDao.deleteAllAnimes()
    }
    suspend fun deleteAnime(id:String){
        favoriteDao.deleteAnime(id)
    }
}