package com.mars.cloveranime.domain

import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.data.FlvDirectoryRepository

class FlvDirectoryUseCase {
    val repository = FlvDirectoryRepository()
    suspend operator fun invoke(category: String, genero: String, page: Int): MutableList<SearchAnimeModel>? =
        repository.getAllAnimes(refactorCategory(category), genero, page)

    private fun refactorCategory(category: String): String{
        return when(category){
            "anime" -> "tv"
            "pelicula" -> "movie"
            "especial" -> "special"
            else -> {"ova"}
        }
    }
}