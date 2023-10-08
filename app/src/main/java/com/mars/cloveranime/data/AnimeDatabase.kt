package com.mars.cloveranime.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mars.cloveranime.data.database.entities.AnimeEntity
import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity
import com.mars.cloveranime.data.database.entities.dao.AnimeDao
import com.mars.cloveranime.data.database.entities.dao.AnimeFinishedDao
import com.mars.cloveranime.data.database.entities.dao.AnimesPendingDao

@Database(
    entities = [AnimeEntity::class, AnimesPendingEntity::class, AnimeFinishedEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AnimeDatabase: RoomDatabase() {
    abstract fun getAnimeDao(): AnimeDao
    abstract fun getAnimePendingDao(): AnimesPendingDao
    abstract fun getAnimeFinishedDao(): AnimeFinishedDao
}