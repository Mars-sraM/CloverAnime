package com.mars.cloveranime.ui.viewModel

import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.CapEmisionModel
import com.mars.cloveranime.domain.EmisionUseCase
import com.mars.cloveranime.domain.FlvEmisionUseCase
import kotlinx.coroutines.launch

class EpisodesEmisionViewModel : ViewModel() {
    val capEmisionModel = MutableLiveData<MutableList<CapEmisionModel>?>()
    val emisionUseCase = EmisionUseCase()
    val flvEmisionUseCase = FlvEmisionUseCase()
    val isloading = MutableLiveData<Boolean>()
    fun addEpisodesEmision(privider: String) {
        viewModelScope.launch {
            when (privider) {
                "MonosChinos" -> {
                    isloading.postValue(false)
                    capEmisionModel.postValue(emisionUseCase())
                    isloading.postValue(true)
                }

                "AnimeFLV" -> {
                    isloading.postValue(false)
                    capEmisionModel.postValue(flvEmisionUseCase())
                    isloading.postValue(true)
                }
            }
        }
    }
}