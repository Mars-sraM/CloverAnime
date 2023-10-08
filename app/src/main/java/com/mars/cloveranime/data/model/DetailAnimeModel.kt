package com.mars.cloveranime.data.model



data class DetailAnimeModel (
    val detailType: String,
    val detailGenres: MutableList<DetailGenres>,
    val detailStatus: String,
    val detailSynopsis: String,
    val detailScore: String
)
data class DetailFull(
    val detail1: DetailAnimeModel,
    val detail2: DetailAnimeModelPart2,
    val detailImages: DetailImageModel
)
data class DetailImageModel(
    val detailimgPerfil: String,
)
data class DetailAnimeModelPart2(
    val detailName: String,
    val detailImg: String,
    val detailDate: String,
    val AnimeUrl: String
)
data class DetailGenres(
    val name:String
)
data class Episodes(
    val animeName: String,
    val noEpisode: String,
    val UrlEpisode: String,
    val imageEpisode: String
)