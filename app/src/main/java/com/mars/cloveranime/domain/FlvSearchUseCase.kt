package com.mars.cloveranime.domain

import com.mars.cloveranime.data.FlvSearchAnimeRepository
import com.mars.cloveranime.data.model.SearchAnimeModel

class FlvSearchUseCase {
    val repository = FlvSearchAnimeRepository()
    suspend operator fun invoke(query: String): MutableList<SearchAnimeModel>? = repository.getAllAnimes(query)
}