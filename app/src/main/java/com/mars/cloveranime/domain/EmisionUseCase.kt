package com.mars.cloveranime.domain

import com.mars.cloveranime.data.EpisodesEmisionRepository
import com.mars.cloveranime.data.model.CapEmisionModel


class EmisionUseCase {
    val repository = EpisodesEmisionRepository()
    suspend operator fun invoke(): MutableList<CapEmisionModel>? = repository.getAnimesEpisodesEmision()
}