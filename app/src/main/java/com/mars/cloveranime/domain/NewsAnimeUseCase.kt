package com.mars.cloveranime.domain

import com.mars.cloveranime.data.NewsAnimeRepository
import com.mars.cloveranime.data.model.NewsModel

class NewsAnimeUseCase {
    val repository = NewsAnimeRepository()
    suspend operator fun invoke(): MutableList<NewsModel>? = repository.getNewsAnime()
}