package com.mars.cloveranime.data

import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity
import com.mars.cloveranime.data.database.entities.dao.AnimeFinishedDao
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.data.model.toDomainFinished
import java.util.Collections
import javax.inject.Inject

class AnimesFinishedRepository @Inject constructor(
    private val finishedDao: AnimeFinishedDao
) {
    suspend fun getAnimeFinished(): List<AnimeFavorite> {
        val response = finishedDao.getListAnimeFinished()

        return response.map { it.toDomainFinished() }
    }
    suspend fun addAnimesFinished(anime: AnimeFinishedEntity) {
        finishedDao.inserAnimeFinished(anime)
    }
    suspend fun addAllAnimeFinished(listAnime: List<AnimeFinishedEntity>) {

        finishedDao.inserAllAnimeFinishedList(listAnime)
    }
    suspend fun clearAnimes(){
        finishedDao.deleteAllAnimesFinished()
    }
    suspend fun deleteAnime(id:String){
        finishedDao.deleteAnimeFinished(id)
    }
}