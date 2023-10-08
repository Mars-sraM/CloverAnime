package com.mars.cloveranime.ui.viewModel

import androidx.lifecycle.ViewModel
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs

class PreferencesViewModel: ViewModel() {

    fun saveMode(option: Boolean) {
        prefs.saveModeOption(option)
    }
    fun getMode(): Boolean = prefs.getModeOption()

}