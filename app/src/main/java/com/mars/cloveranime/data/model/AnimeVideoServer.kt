package com.mars.cloveranime.data.model

data class AnimeVideoServer (
    val sizeServers: Int,
    val listServer: MutableList<Server>
)
data class Server (
    val serverName: String,
    val linkVideo: String
)