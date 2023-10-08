package com.mars.cloveranime.data
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity
import com.mars.cloveranime.data.database.entities.dao.AnimesPendingDao
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.data.model.toDomainPending
import java.util.Collections
import javax.inject.Inject

class AnimesPendingRepository  @Inject constructor(
    private val pendingDao: AnimesPendingDao
) {

    suspend fun getAnimesPending(): List<AnimeFavorite> {
        val response = pendingDao.getListAnimePending()

        return response.map { it.toDomainPending() }
    }
    suspend fun addAnimePending(anime: AnimesPendingEntity) {
        pendingDao.inserAnimePending(anime)
    }
    suspend fun addAllAnimePending(listAnime: List<AnimesPendingEntity>) {

        pendingDao.inserAllAnimeList(listAnime)
    }
    suspend fun clearAnimesPending(){
        pendingDao.deleteAllAnimesPending()
    }
    suspend fun deleteAnimePending(id:String){
        pendingDao.deleteAnimePending(id)
    }
}