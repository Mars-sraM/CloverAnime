package com.mars.cloveranime.core

import com.mars.cloveranime.data.database.entities.AnimeEntity
import com.mars.cloveranime.data.database.entities.AnimeFinishedEntity
import com.mars.cloveranime.data.database.entities.AnimesPendingEntity
import com.mars.cloveranime.data.model.AnimeFavorite

object RefactorDataClass {
    fun AnimeEntity.toPending() = AnimesPendingEntity(
        animeName = animeName,
        animeUrl =  animeUrl,
        animeimg = animeimg,
    )
    fun AnimeFavorite.toPending() = AnimesPendingEntity(
        animeName = animeName,
        animeUrl =  animeUrl,
        animeimg = animeimg,
    )
    fun AnimeEntity.toFinished() = AnimeFinishedEntity(
        animeName = animeName,
        animeUrl =  animeUrl,
        animeimg = animeimg,
    )
    fun AnimeFavorite.toFinished() = AnimeFinishedEntity(
        animeName = animeName,
        animeUrl =  animeUrl,
        animeimg = animeimg,
    )
    fun AnimeFavorite.toFavorite() = AnimeEntity(
        animeName = animeName,
        animeUrl =  animeUrl,
        animeimg = animeimg,
    )

}