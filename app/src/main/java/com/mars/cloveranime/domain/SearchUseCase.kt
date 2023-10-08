package com.mars.cloveranime.domain

import com.mars.cloveranime.data.SearchAnimeRepository
import com.mars.cloveranime.data.model.SearchAnimeModel

class SearchUseCase {

    val repository = SearchAnimeRepository()
    suspend operator fun invoke(query: String): MutableList<SearchAnimeModel>? = repository.getAllAnimes(query)
}