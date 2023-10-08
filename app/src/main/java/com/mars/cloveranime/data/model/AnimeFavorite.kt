package com.mars.cloveranime.data.model

import com.mars.cloveranime.data.database.entities.AnimeEntity
import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity

data class AnimeFavorite(
    val animeName: String,
    val animeUrl: String,
    val animeimg: String,
)
fun AnimeEntity.toDomain() = AnimeFavorite(animeName, animeUrl, animeimg)
fun AnimesPendingEntity.toDomainPending() = AnimeFavorite(animeName, animeUrl, animeimg)
fun AnimeFinishedEntity.toDomainFinished() = AnimeFavorite(animeName, animeUrl, animeimg)