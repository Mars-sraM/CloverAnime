package com.mars.cloveranime.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity

@Dao
interface AnimesPendingDao {
    @Query("SELECT * FROM anime_pending_table")
    suspend fun getListAnimePending(): List<AnimesPendingEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserAnimePending(anime: AnimesPendingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserAllAnimeList(listAnime: List<AnimesPendingEntity>)

    @Query("DELETE FROM anime_pending_table")
    suspend fun deleteAllAnimesPending()

    @Query("DELETE FROM anime_pending_table WHERE animeUrl =:id")
    suspend fun deleteAnimePending(id: String)

}