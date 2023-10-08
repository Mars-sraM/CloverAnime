package com.mars.cloveranime.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mars.cloveranime.data.database.entities.AnimeEntity

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime_favorite_table")

    suspend fun getListFavorites(): List<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun inserAnime(anime: AnimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserAllAnimeList(listAnime: List<AnimeEntity>)

    @Query("DELETE FROM anime_favorite_table")
    suspend fun deleteAllAnimes()

    @Query("DELETE FROM anime_favorite_table WHERE animeUrl =:id")
    suspend fun deleteAnime(id: String)
}