package com.mars.cloveranime.ui.view

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import com.mars.cloveranime.R
import com.mars.cloveranime.data.SharedPreferencesCA
import com.mars.cloveranime.databinding.ActivityPlayerBinding
import com.mars.cloveranime.ui.viewModel.DetailViewModel

class PlayerActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_OPTION = "extra_option"
        const val EXTRA_PROVIDER = "extra_provider"
    }

    private val detailViewModel: DetailViewModel by viewModels()
    lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val episodeUrl: String = intent.getStringExtra(EXTRA_URL).orEmpty()
        val provider: String = intent.getStringExtra(EXTRA_PROVIDER).orEmpty()
        val option: Int = intent.getIntExtra(EXTRA_OPTION, -1)
        hideSystemUI()
        getDetail(episodeUrl, option, provider)

    }

    @SuppressLint("SetJavaScriptEnabled")
    fun getDetail(url: String, option: Int, provider: String) {

        detailViewModel.addVideosEpisodes(url, option, provider)
        detailViewModel.episodesVideoModel.observe(this, Observer {
            var video = ""
            when (provider) {
                "MonosChinos" -> video = "https://monoschinos2.com/reproductor?url=$it"
                "AnimeFLV" -> video = it
            }

            val settings = binding.wvPlayer.settings
            binding.wvPlayer.setBackgroundColor(Color.TRANSPARENT)
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            settings.setSupportMultipleWindows(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT

            binding.wvPlayer.webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                }
            }
            binding.wvPlayer.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (view!!.url != video) {
                        view.stopLoading()
                        return super.shouldOverrideUrlLoading(view, request)
                    } else {
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.wvPlayer.visibility = View.VISIBLE
                }

            }

            binding.wvPlayer.loadUrl(video)
            saveWatch(url, provider)
        })
    }

    private fun saveWatch(url: String, provider: String) {
        var id = ""
        when (provider) {
            "MonosChinos" -> id = url.replace("https://monoschinos2.com/ver/", "")
            "AnimeFLV" -> id = url.replace("https://www3.animeflv.net/ver/", "")
        }

        SharedPreferencesCA.prefs.saveWatchOption(id, R.drawable.ic_visto)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    private fun hideSystemUI() {
        val decorView: View = window.decorView
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    private fun showSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        //getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun onDestroy() {
        super.onDestroy()
        showSystemUI()

        binding.wvPlayer.visibility = View.GONE
        binding.wvPlayer.clearHistory()
        binding.wvPlayer.clearCache(true)
        binding.wvPlayer.removeAllViews()
        binding.wvPlayer.destroy()

    }
}
