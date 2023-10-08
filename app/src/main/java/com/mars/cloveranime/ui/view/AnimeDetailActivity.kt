package com.mars.cloveranime.ui.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.iamageo.library.AnotherReadMore
import com.mars.cloveranime.R
import com.mars.cloveranime.core.RefactorDataClass.toFinished
import com.mars.cloveranime.core.RefactorDataClass.toPending
import com.mars.cloveranime.data.database.entities.AnimeEntity
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.databinding.ActivityAnimeDetailBinding
import com.mars.cloveranime.ui.view.Adapters.CategoryAdapter
import com.mars.cloveranime.ui.view.fragments.EpisodesFragment
import com.mars.cloveranime.ui.viewModel.AnimeFavoriteModel
import com.mars.cloveranime.ui.viewModel.AnimesFinishedViewModel
import com.mars.cloveranime.ui.viewModel.AnimesPendingViewModel
import com.mars.cloveranime.ui.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File



@AndroidEntryPoint
class AnimeDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_PROVIDER = "extra_provider"
    }

    private lateinit var rbFavoritos: CheckBox
    private lateinit var dialog: Dialog
    private lateinit var rbPendientes: CheckBox
    private lateinit var rbTerminados: CheckBox
    private lateinit var url: String
    private var detailImage = ""
    lateinit var binding: ActivityAnimeDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val favoritesViewModel: AnimeFavoriteModel by viewModels()
    private val pendingViewModel: AnimesPendingViewModel by viewModels()
    private val finishedViewModel: AnimesFinishedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        url = intent.getStringExtra(EXTRA_ID).orEmpty()
        val provider = intent.getStringExtra(EXTRA_PROVIDER).orEmpty()
        initContenDialog()
        iniUi(url, provider)
        detailViewModel.addDetailPart2(url, provider)
        detailViewModel.detailModelPart2.observe(this, Observer { detailAnimePart2 ->
            if (detailAnimePart2 != null) {
                getFavoriteOption(detailAnimePart2,url)
            }
        })
    }

    private fun iniUi(url: String, provider: String) {
        detailViewModel.addDetailImages(url, provider)
        detailViewModel.detailImageModel.observe(this, Observer { detailAnimeImg ->
            if (detailAnimeImg != null) {
                detailImage = detailAnimeImg.detailimgPerfil
                if (detailAnimeImg.detailimgPerfil.isEmpty()){
                    Glide.with(binding.ivAnimeImg).load(R.drawable.ic_internet_off).into(binding.ivAnimeImg)
                }else{
                    Glide.with(binding.ivAnimeImg).load(detailAnimeImg.detailimgPerfil).skipMemoryCache(true)
                        .into(binding.ivAnimeImg)
                }
                binding.loadingAnimeImg.isVisible = false
                binding.linearBottoms.isVisible = true
                binding.loadingLinearBottoms.isVisible = false

            }
        })
        detailViewModel.addDetailPart2(url, provider)
        detailViewModel.detailModelPart2.observe(this, Observer { detailAnimePart2 ->
            if (detailAnimePart2 != null) {
                Glide.with(binding.ivPortada).load(detailAnimePart2.detailImg).skipMemoryCache(true)
                    .into(binding.ivPortada)
                binding.tvAnimeName.text = detailAnimePart2.detailName
                binding.tvAnimeAO.text = detailAnimePart2.detailDate.ifEmpty { "N/A" }

                binding.addButtom.setOnClickListener {
                    if (binding.addButtom.text == getString(R.string.text_buttom_add)){
                        showDialogFavorites(detailAnimePart2)
                    }
                }

            }
        })
        detailViewModel.addDetail(url, provider)
        detailViewModel.detailModel.observe(this, Observer { detailAnime ->
            if (detailAnime != null) {
                binding.tvScore.text = detailAnime.detailScore
                binding.tvStatus.text = detailAnime.detailStatus
                binding.tvType.text = detailAnime.detailType
                //binding.tvSynapsis.movementMethod = ScrollingMovementMethod()
                val anotherReadMore: AnotherReadMore = AnotherReadMore.Builder(this)
                    .textLength(400, AnotherReadMore.TYPE_LINE)
                    .moreLabel("Ver mas")
                    .lessLabel("Ver menos")
                    .build()
                anotherReadMore.addReadMoreTo(
                    binding.tvSynapsis,
                    detailAnime.detailSynopsis.ifEmpty { "La sinopsis de este anime no está disponible." }
                )

                val adapter = CategoryAdapter(detailAnime.detailGenres)
                binding.rvCategoriesDatail.setHasFixedSize(true)
                binding.rvCategoriesDatail.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.rvCategoriesDatail.adapter = adapter

            }
        })
        detailViewModel.isloading.observe(this, Observer {
                binding.ivAnimeImg.isVisible = it
            })

        binding.buttomCap.setOnClickListener {
            var fragment: Fragment? = supportFragmentManager.findFragmentByTag("episodes_fragment")
            if (fragment == null) {
                fragment = EpisodesFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.detailContainer, fragment, "episodes_fragment")
                    .commit()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun showDialogFavorites(anime: DetailAnimeModelPart2) {
        val animeUrl = if (url.contains("https://monoschinos2.com")){
            url.replace("https://monoschinos2.com/anime/", "")
        } else {
            url.replace("https://www3.animeflv.net/anime/", "")
        }
        println("la url: $animeUrl")
        val favorite = AnimeEntity(
            animeUrl = animeUrl,
            animeName = anime.detailName.replace(",", ""),
            animeimg = isReplace(detailImage)
        )

        rbFavoritos.setOnCheckedChangeListener { _, value ->
            //saveOption(KEY_FAVORITE, value)
            if (value) {
                favoritesViewModel.addAnimes(favorite)
                btnUi("Favorito", getDrawable(R.drawable.ic_favorite))
                binding.addButtom.setOnClickListener {
                    //saveOption(KEY_FAVORITE, false)
                    favoritesViewModel.deleteAnime(favorite.animeUrl)
                    trueAction(anime, rbFavoritos)
                }
                dialog.dismiss()
            }
        }
        rbPendientes.setOnCheckedChangeListener { _, value ->
            val pending = favorite.toPending()
            if (value) {
                pendingViewModel.addAnimes(pending)
                btnUi("Pendiente", getDrawable(R.drawable.ic_pendientes))
                binding.addButtom.setOnClickListener {
                    pendingViewModel.deleteAnime(pending.animeUrl)
                    trueAction(anime, rbPendientes)
                }
                dialog.dismiss()
            }
        }

        rbTerminados.setOnCheckedChangeListener { _, value ->
            if (value) {
                finishedViewModel.addAnimes(favorite.toFinished())
                btnUi("Terminado", getDrawable(R.drawable.ic_terminados_true))
                binding.addButtom.setOnClickListener {
                    finishedViewModel.deleteAnime(favorite.toFinished().animeUrl)
                    trueAction(anime, rbTerminados)
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun initContenDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_add_favorites)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        rbFavoritos = dialog.findViewById(R.id.rbFavoritos)
        rbPendientes = dialog.findViewById(R.id.rbPendientes)
        rbTerminados = dialog.findViewById(R.id.rbTerminados)
    }


    private fun isReplace(original: String): String {
        var reemplazo = original.replace(
            "https://www3.animeflv.net/uploads/animes/covers/", ""
        )
        if (original == reemplazo) {
            reemplazo = original.replace(
                "https://monoschinos2.com/thumbs/imagen/", ""
            )
        }
        return reemplazo
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getFavoriteOption(anime: DetailAnimeModelPart2, url: String){
        val animeUrl = if (url.contains("https://monoschinos2.com")){
            url.replace("https://monoschinos2.com/anime/", "")
        } else {
            url.replace("https://www3.animeflv.net/anime/", "")
        }
        val favorite = AnimeEntity(
            animeUrl = animeUrl,
            animeName = anime.detailName.replace(",", ""),
            animeimg = isReplace(detailImage)
        )
        favoritesViewModel.getAnimes(this)
        favoritesViewModel.animesFavorites.observe(this, Observer { favorites ->
            for (i in 0 until favorites.size) {
                val dbUrl = favorites.get(i).animeUrl
                if (dbUrl == animeUrl) {
                    btnUi("Favorito", getDrawable(R.drawable.ic_favorite))
                    binding.addButtom.setOnClickListener {
                        //saveOption(KEY_FAVORITE, false)
                        favoritesViewModel.deleteAnime(favorite.animeUrl)
                        trueAction(anime, rbFavoritos)
                    }
                }
            }
        })
        pendingViewModel.getAnimes(this)
        pendingViewModel.animesPending.observe(this, Observer { pending ->
            for (i in 0 until pending.size) {
                val dbUrl = pending.get(i).animeUrl
                val pendingEntity = favorite.toPending()
                if (dbUrl == animeUrl) {
                    btnUi("Pendiente", getDrawable(R.drawable.ic_pendientes))
                    binding.addButtom.setOnClickListener {
                        //saveOption(KEY_PENDIENTES, false)
                        pendingViewModel.deleteAnime(pendingEntity.animeUrl)
                        trueAction(anime, rbPendientes)
                    }
                }
            }
        })
        finishedViewModel.getAnimes(this)
        finishedViewModel.animesFinished.observe(this, Observer { finished ->
            for (i in 0 until finished.size) {
                val dbUrl = finished.get(i).animeUrl
                if (dbUrl == animeUrl) {
                    val finishedEntity = favorite.toFinished()
                    btnUi("Terminado", getDrawable(R.drawable.ic_terminados_true))
                    binding.addButtom.setOnClickListener {
                       // saveOption(KEY_TERMINADOS, false)
                        finishedViewModel.deleteAnime(finishedEntity.animeUrl)
                        trueAction(anime, rbTerminados)
                    }
                }
            }
        })


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun trueAction(anime: DetailAnimeModelPart2, checkBox: CheckBox) {
        binding.addButtom.setCompoundDrawablesWithIntrinsicBounds(
            getDrawable(R.drawable.ic_add_anime), null, null, null
        )
        binding.addButtom.text = getString(R.string.text_buttom_add)
        binding.addButtom.setOnClickListener {
            checkBox.isChecked = false
            showDialogFavorites(anime)
        }
    }

    private fun btnUi(status: String, icon: Drawable?){
        binding.addButtom.setCompoundDrawablesWithIntrinsicBounds(
            icon, null, null, null
        )
        binding.addButtom.text = status
    }
    fun clearCache() {
        Log.i(ContentValues.TAG, "Clearing Cache.")
        val dir: Array<File> = cacheDir.listFiles() as Array<File>
        for (f in dir) {
            f.delete()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache()
    }
}