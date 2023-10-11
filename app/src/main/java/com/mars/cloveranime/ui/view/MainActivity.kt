package com.mars.cloveranime.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mars.cloveranime.R
import com.mars.cloveranime.core.CheckInternet
import com.mars.cloveranime.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    var backPressedTime: Long = 0


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        getSize()
    }
    private fun initUi(){
        val internet = CheckInternet.isInternetOn(this)
        if (!internet){
            Toast.makeText(this, "No hay acceso a internet", Toast.LENGTH_SHORT).show()
        }
        initNavigation()
        closeAppTwoBacks()
    }
    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun closeAppTwoBacks(){
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (backPressedTime + 3000 > System.currentTimeMillis()) {
                    finishAffinity()
                } else {
                    Toast.makeText(this@MainActivity, "Presione una vez más para salir.", Toast.LENGTH_LONG)
                        .show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })
    }
    private fun getSize(){
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val widthInDp = (metrics.widthPixels / metrics.density).toInt()
        println("el ancho de la pantalla es $widthInDp")
        println("el ancho de la pantalla es ${metrics.widthPixels}")
    }

}