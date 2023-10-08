package com.mars.cloveranime.data

import android.content.Context
import android.widget.CheckBox
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.MylistModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Prefs(context: Context) {

    private val SHARED_NAME = "watch_options"
    private val SHARED_USER_NAME = "user_info"
    private val SHARED_PROVIDER_NAME = "name_provider"
    private val SHARED_MODE_OPTION = "mode_provider"
    private val storage = context.getSharedPreferences(SHARED_NAME, 0)
    private val storageUser = context.getSharedPreferences(SHARED_USER_NAME, 0)
    private val storageProvider = context.getSharedPreferences(SHARED_PROVIDER_NAME, 0)
    private val storageMode = context.getSharedPreferences(SHARED_MODE_OPTION, 0)

    fun saveUserInfo(name: String, email: String){
        storageUser.edit().putString("name",name).apply()
        storageUser.edit().putString("email",email).apply()
    }
    fun getUserInfo(): List<String>{
        val name = storageUser.getString("name", "no")
        val email = storageUser.getString("email", "no")
        return  listOf<String>(name!!, email!!)
    }
    fun wipeUserInfo(){
        storageUser.edit().clear().apply()
    }
    fun saveWatchOption(name: String, id: Int) {
        storage.edit().putInt(name, id).apply()
    }

    fun getWatchOption(name: String): Int {
        return storage.getInt(name, R.drawable.ic_no_visto)
    }

    fun wipe(name: String){
        storage.edit().remove(name).apply()
    }

    fun saveProvider(name: String) {
        storageProvider.edit().putString("provider", name).apply()
    }

    fun getProvider(): String {
        return storageProvider.getString("provider", "MonosChinos").toString()
    }
    fun saveModeOption(option: Boolean) {
        storageProvider.edit().putBoolean("modeOption", option).apply()
    }

    fun getModeOption(): Boolean {
        return storageProvider.getBoolean("modeOption", false)
    }
}