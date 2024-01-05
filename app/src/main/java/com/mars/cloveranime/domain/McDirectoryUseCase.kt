package com.mars.cloveranime.domain

import com.mars.cloveranime.data.McDirectoryRepository
import com.mars.cloveranime.data.model.SearchAnimeModel

class McDirectoryUseCase {

    val repository = McDirectoryRepository()
    suspend operator fun invoke(category: String, genero: String, page: Int): MutableList<SearchAnimeModel>? =
        repository.getAllAnimes(category, genero, page)
}