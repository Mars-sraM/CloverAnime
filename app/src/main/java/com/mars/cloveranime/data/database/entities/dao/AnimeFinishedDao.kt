package com.mars.cloveranime.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity

@Dao
interface AnimeFinishedDao {

    @Query("SELECT * FROM anime_finished_table")
    suspend fun getListAnimeFinished(): List<AnimeFinishedEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserAnimeFinished(anime: AnimeFinishedEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserAllAnimeFinishedList(listAnime: List<AnimeFinishedEntity>)

    @Query("DELETE FROM anime_finished_table")
    suspend fun deleteAllAnimesFinished()

    @Query("DELETE FROM anime_finished_table WHERE animeUrl =:id")
    suspend fun deleteAnimeFinished(id: String)

}