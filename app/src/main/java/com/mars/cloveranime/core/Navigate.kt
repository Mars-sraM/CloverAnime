package com.mars.cloveranime.core


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.view.PlayerActivity


object Navigate {
//claveProvider: String, provider: String
    fun navigateActivities(url: String, context: Context, clave:String, claveProvider: String, provider: String){
        val intent = Intent(context, AnimeDetailActivity::class.java)
        intent.putExtra(clave, url)
        intent.putExtra(claveProvider, provider)
        context.startActivity(intent)
    }

    fun navigateActivityPlayer(
        url: String,
        context: Context,
        clave:String, clave2: String,
        videoUrl: String,claveProvider: String, provider: String
    ){
        val intent = Intent(context, PlayerActivity::class.java)
        intent.putExtra(clave, url)
        intent.putExtra(clave2,videoUrl)
        intent.putExtra(claveProvider, provider)
        context.startActivity(intent)
    }

}