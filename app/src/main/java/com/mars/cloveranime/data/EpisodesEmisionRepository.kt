package com.mars.cloveranime.data

import com.mars.cloveranime.data.model.CapEmisionModel
import com.mars.cloveranime.data.network.CapEmisionRequest
import com.mars.cloveranime.data.network.FlvEmisionRequest

class EpisodesEmisionRepository {
    val jsoupResponse = CapEmisionRequest()
    val jsoupFlvResponse = FlvEmisionRequest()
    suspend fun getAnimesEpisodesEmision(): MutableList<CapEmisionModel> {
        val result = jsoupResponse.requestCapEmision()
        return result
    }
    suspend fun getAnimesFlvEpisodesEmision(): MutableList<CapEmisionModel> {
        val result = jsoupFlvResponse.requestCapEmision()
        return result
    }

}