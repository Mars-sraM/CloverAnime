package com.mars.cloveranime.data.model

data class CapEmisionModel(
    val name: String,
    val image: String,
    val capNunero: String,
    val type: String,
    val url: AnimeUrl
    )
data class AnimeUrl(
    val capUrl: String,
    val animeUrl: String
)
