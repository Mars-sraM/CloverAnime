package com.mars.cloveranime.data

import android.app.Application
import android.content.Context
import android.widget.CheckBox
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mars.cloveranime.data.model.MylistModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltAndroidApp
class SharedPreferencesCA: Application() {

    companion object{
        lateinit var prefs: Prefs

    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }

}