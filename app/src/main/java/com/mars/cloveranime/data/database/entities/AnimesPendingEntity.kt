package com.mars.cloveranime.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_pending_table")
data class AnimesPendingEntity(
    @PrimaryKey
    @ColumnInfo(name = "animeUrl") val animeUrl: String,
    @ColumnInfo(name = "animeName") val animeName: String,
    @ColumnInfo(name = "animeimg") val animeimg: String,
)