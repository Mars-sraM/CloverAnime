package com.mars.cloveranime.domain

import com.mars.cloveranime.data.DetailAnimeRepository
import com.mars.cloveranime.data.model.DetailAnimeModel
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.DetailImageModel

import com.mars.cloveranime.data.model.SearchAnimeModel

class DetailUseCase {
    val repository = DetailAnimeRepository()
    suspend  fun detailImageUseCase(url: String): DetailImageModel? = repository.getAnimesImages(url)
    suspend  fun detailPart1UseCase(url: String): DetailAnimeModel? = repository.getDetailAnimesPart1(url)
    suspend operator fun invoke(url: String): DetailAnimeModelPart2? = repository.getDetailAnimesPart2(url)
}