package com.mars.cloveranime.ui.view.fragments

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.mars.cloveranime.R
import com.mars.cloveranime.core.CheckInternet
import com.mars.cloveranime.core.Navigate
import com.mars.cloveranime.data.GenresProvider
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs
import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.databinding.FragmentDirectoryBinding
import com.mars.cloveranime.ui.view.Adapters.CalendarAdapter
import com.mars.cloveranime.ui.view.Adapters.SearchAnimeAdapter
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.viewModel.AnimeDirectoryViewModel
import java.io.File


class DirectoryFragment : Fragment() {
    private var _binding: FragmentDirectoryBinding? = null
    private val binding get() = _binding!!
    private val directoryViewModel: AnimeDirectoryViewModel by viewModels()
    private var calendarAdapter: CalendarAdapter? = null
    private lateinit var directoryAdapter: SearchAnimeAdapter
    private val dayList: ArrayList<String> = arrayListOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )
    private var genre = ""
    private var genresList = emptyList<String>()
    private var genresValuesList = emptyList<String>()

    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.to_bottom_anim
        )
    }
    private var clicked = false
    private var clickedGenres = false
    private var contador = 1
    private var categoria = "anime"
    private var maxSize = 0
    private var listSize = 0
    private var directoryAnimeList: MutableList<SearchAnimeModel> =
        emptyList<SearchAnimeModel>().toMutableList()
    private val layoutManagerDir = GridLayoutManager(context, 3)
    private lateinit var provider: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val internet = CheckInternet.isInternetOn(requireContext())
        provider = prefs.getProvider()
        initUi(internet, provider)
    }


    private fun initUi(internet: Boolean, provider: String) {
        binding.fabCategory.isVisible = true
        initRecyclerViewDirectory(provider)
        getAnimesDirectory("anime", "false", 1, provider)
        addListGenres(provider)
        initDropdownDirectory(genresList)
        binding.tvProvider.text = provider

        binding.ivSearch.setOnClickListener {
            if (internet) {
                var fragment: Fragment? =
                    requireActivity().supportFragmentManager.findFragmentByTag("search_fragment")
                if (fragment == null) {
                    fragment = SearchFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.ContainerPrincipal, fragment, "search_fragment")
                        .commit()
                }
            } else {
                Toast.makeText(requireContext(), "No hay acceso a internet", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        binding.fabCategory.setOnClickListener {
            onAddBottomClicked()
        }
        binding.fabAnime.setOnClickListener {
            onAddBottomClicked()
            getAnimesDirectory("anime", genre, 1, provider)
        }
        binding.fabMovie.setOnClickListener {
            onAddBottomClicked()
            getAnimesDirectory("pelicula", genre, 1, provider)
        }
        binding.fabSpecial.setOnClickListener {
            onAddBottomClicked()
            getAnimesDirectory("especial", genre, 1, provider)
        }
        binding.fabOva.setOnClickListener {
            onAddBottomClicked()
            getAnimesDirectory("ova", genre, 1, provider)
        }
        binding.ivFilterGenres.setOnClickListener {
            setBtnGerenresvisibility(clickedGenres)
            clickedGenres = !clickedGenres
        }

    }
    private fun initDropdownDirectory(genresList: List<String>) {
        binding.dropdownGenres.setDropDownBackgroundResource(R.color.colorPrimaryVariant)
        val dropdownAdapter = ArrayAdapter(
            requireContext(), R.layout.item_dropdown, genresList
        )
        binding.dropdownGenres.setAdapter(dropdownAdapter)
        binding.dropdownGenres.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val onItemselected = adapterView.getItemAtPosition(i)
                onItemSelectedDirectory(i)
            }
    }

    private fun onItemSelectedDirectory(item: Int) {
        getAnimesDirectory(categoria, genresValuesList[item], 1, provider)
    }

    private fun getAnimesDirectory(category: String, genero: String, page: Int, provider: String) {
        contador = 1
        directoryViewModel.addDirectoryAnimes(provider, category, genero, page)
        directoryViewModel.animes.observe(viewLifecycleOwner, Observer { animes ->
            if (!animes.isNullOrEmpty()) {
                binding.ivError.isVisible = false
                directoryAnimeList.clear()
                directoryAnimeList.addAll(animes)
                directoryAdapter.notifyDataSetChanged()
                listSize = animes.size
            }
        })
        binding.ivMasAnimes.setOnClickListener {
            seeMoreAnimes(category, genero, provider)
        }
        categoria = category
    }
    private fun seeMoreAnimes(category: String, genero: String, provider: String) {
        contador++
        directoryViewModel.pageAnimes.removeObservers(viewLifecycleOwner)
        directoryViewModel.addPageDirectoryAnimes(provider, category, genero, contador)
        directoryViewModel.pageAnimes.observe(viewLifecycleOwner, Observer { animes ->
            if (!animes.isNullOrEmpty()) {
                binding.ivError.isVisible = false
                val size = directoryAnimeList.size
                directoryAnimeList.addAll(animes)
                directoryAdapter.notifyItemRangeInserted(size, animes.size)
                binding.rvAnimes.smoothScrollToPosition(size)
                listSize = animes.size
            }
        })
       
    }

    private fun initRecyclerViewDirectory(provider: String) {
        binding.ivError.isVisible = false
        directoryAdapter = SearchAnimeAdapter(directoryAnimeList) {
            Navigate.navigateActivities(
                it, requireContext(), AnimeDetailActivity.EXTRA_ID,
                AnimeDetailActivity.EXTRA_PROVIDER, provider
            )
        }

        initRecyclerView(true, layoutManagerDir, binding.rvDirectoryLoading)
        binding.rvAnimes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && listSize >= maxSize) {
                    binding.ivMasAnimes.isVisible = true
                    if(clicked){
                        binding.fabCategory.performClick()
                    }
                    binding.fabCategory.isVisible = false

                }else if(!recyclerView.canScrollVertically(1) && directoryAdapter.itemCount < 12){
                    binding.ivMasAnimes.isVisible = false
                    if(clicked){
                        binding.fabCategory.performClick()
                    }
                    binding.fabCategory.isVisible = true
                } else {
                    binding.ivMasAnimes.isVisible = false
                    binding.fabCategory.isVisible = recyclerView.canScrollVertically(1)
                }
            }
        })
    }

    private fun onAddBottomClicked() {
        setFabCategoryVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setFabCategoryVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabCategory.text="Cancelar"
            binding.fabMovie.isVisible = true
            binding.fabSpecial.isVisible = true
            binding.fabOva.isVisible = true
            binding.fabAnime.isVisible = true
        } else {
            binding.fabCategory.text="Categoría"
            binding.fabMovie.isVisible = false
            binding.fabSpecial.isVisible = false
            binding.fabOva.isVisible = false
            binding.fabAnime.isVisible = false

        }
    }
    private fun setBtnGerenresvisibility(clicked: Boolean){
        if (!clicked) {
            binding.linearGenres.isVisible = true
            binding.ivFilterGenres.setImageResource(R.drawable.ic_filter_list_off)
        } else {
            binding.linearGenres.isVisible = false
            binding.ivFilterGenres.setImageResource(R.drawable.ic_filter_list)
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabMovie.startAnimation(fromBottom)
            binding.fabSpecial.startAnimation(fromBottom)
            binding.fabOva.startAnimation(fromBottom)
            binding.fabAnime.startAnimation(fromBottom)
        } else {
            binding.fabMovie.clearAnimation()
            binding.fabSpecial.clearAnimation()
            binding.fabOva.clearAnimation()
            binding.fabAnime.clearAnimation()

        }
    }

    private fun initRecyclerView(bool: Boolean, layoutManager: LayoutManager, loading: View) {
        val adapter = if (bool) {
            directoryAdapter
        } else {
            calendarAdapter
        }
        binding.rvAnimes.setHasFixedSize(true)
        binding.rvAnimes.layoutManager = layoutManager
        binding.rvAnimes.adapter = adapter

        directoryViewModel.isloading.observe(viewLifecycleOwner, Observer {
            loading.isVisible = !it
            binding.rvAnimes.isVisible = it
            if (adapter!!.itemCount == 0) {
                binding.ivError.isVisible = it
                binding.rvAnimes.isVisible = false
            } else {
                binding.ivError.isVisible = false
            }

        })
        directoryViewModel.isloadingPage.observe(viewLifecycleOwner, Observer {
            binding.ivMasAnimes.isVisible = it
        })
    }
    private fun initRecyclerViewCalendar(recyclerView: RecyclerView) {
        binding.ivError.isVisible = false
        calendarAdapter = CalendarAdapter {
            Navigate.navigateActivities(
                it, requireContext(), AnimeDetailActivity.EXTRA_ID,
                AnimeDetailActivity.EXTRA_PROVIDER, "MonosChinos"
            )
        }
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = calendarAdapter

        directoryViewModel.isloading.observe(viewLifecycleOwner, Observer {
            binding.rvDirectoryLoading.isVisible = !it
            binding.rvAnimes.isVisible = it
            if (calendarAdapter!!.itemCount == 0) {
                binding.ivError.isVisible = it
                binding.rvAnimes.isVisible = false
            } else {
                binding.ivError.isVisible = false
            }

        })
    }

    private fun initSpinner(genresList: List<String>) {
        val dropdownAdapter = ArrayAdapter(
            requireContext(), R.layout.item_dropdown, genresList
        )
        binding.dropdownGenres.setAdapter(dropdownAdapter)
        binding.dropdownGenres.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                val onItemselected = adapterView.getItemAtPosition(i).toString()
            }

    }

    private fun onItemSelectedCalendar(p0: AdapterView<*>?, p2: Int) {

        when (p0!!.getItemAtPosition(p2).toString()) {
            "Lunes" -> {
                getListAnimeCalendar(0)
            }

            "Martes" -> {
                getListAnimeCalendar(1)
            }

            "Miércoles" -> {
                getListAnimeCalendar(2)
            }

            "Jueves" -> {
                getListAnimeCalendar(3)
            }

            "Viernes" -> {
                getListAnimeCalendar(4)
            }

            "Sábado" -> {
                getListAnimeCalendar(5)
            }

            "Domingo" -> {
                getListAnimeCalendar(6)
            }
        }
    }

    private fun getListAnimeCalendar(day: Int) {
        directoryViewModel.addListAnimes(day)
        directoryViewModel.calendar.observe(viewLifecycleOwner, Observer { animes ->
            if (!animes.isNullOrEmpty()) {
                binding.ivError.isVisible = false
                calendarAdapter!!.updateList(animes)
            }
        })
    }

    fun clearCache() {
        Log.i(ContentValues.TAG, "Clearing Cache.")
        val dir: Array<File> = requireContext().cacheDir.listFiles() as Array<File>
        for (f in dir) {
            f.delete()
        }
    }

    override fun onResume() {
        super.onResume()
        val dropdownAdapter = ArrayAdapter(
            requireContext(), R.layout.item_dropdown, genresList
        )
        binding.dropdownGenres.setAdapter(dropdownAdapter)
        binding.ivMasAnimes.isVisible = false
    }

    private fun addListGenres(provider: String) {
        if (provider == "MonosChinos") {
            genresList = GenresProvider.mcGenresList
            genresValuesList = GenresProvider.mcGenresValuesList
            genre = "false"
            maxSize = 30
            binding.tvProvider.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_monos_chinos, 0, 0, 0
            )
        } else {
            genresList = GenresProvider.flvGenresList
            genresValuesList = GenresProvider.flvGenresValuesList
            genre = ""
            maxSize = 24
            binding.tvProvider.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_animeflv, 0, 0, 0
            )
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        clearCache()
    }
}