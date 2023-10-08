package com.mars.cloveranime.ui.view.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mars.cloveranime.R
import com.mars.cloveranime.core.Navigate
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs
import com.mars.cloveranime.databinding.FragmentSearchBinding
import com.mars.cloveranime.ui.view.Adapters.SearchAnimeAdapter
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.view.AnimeDetailActivity.Companion.EXTRA_ID
import com.mars.cloveranime.ui.viewModel.SearchViewModel
import java.io.File


class SearchFragment : Fragment() {

    // lateinit var adapter:SearchAnimeAdapter
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private var adapter: SearchAnimeAdapter? = null
    private val handler: Handler = Handler()
    private var runnable: Runnable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backPressed()
        val provider = prefs.getProvider()
        initUi(provider)
        binding.SearchView.queryHint = "Buscar en $provider"
        binding.SearchView.maxWidth = Integer.MAX_VALUE
        binding.SearchView.isIconified = false
        binding.SearchView.requestFocus()

        binding.SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchViewModel.addListAnimes(query.orEmpty(), provider)
                searchViewModel.animesModel.observe(viewLifecycleOwner, Observer { animes ->
                    if (!animes.isNullOrEmpty()) {
                        binding.ivError.isVisible = false
                        binding.tvMensajeSearh.isVisible = false
                        adapter!!.updateList(animes)
                    } else {
                        adapter!!.updateList(emptyList())
                        binding.ivError.isVisible = true
                    }
                })
                //searchAnimes(query.orEmpty(), provider)
                binding.SearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.orEmpty().length >= 3) {
                    binding.tvMensajeSearh.isVisible = false
                    if(runnable != null){
                        handler.removeCallbacks(runnable!!)
                    }
                    runnable = Runnable { searchViewModel.addListAnimes(newText!!, provider) }
                    handler.postDelayed(runnable!!, 500)
                    searchViewModel.animesModel.observe(viewLifecycleOwner, Observer { animes ->
                        if (!animes.isNullOrEmpty()) {
                            val list = animes.distinct()
                            adapter!!.updateList(list)
                        }
                    })
                    if (adapter != null) {
                        adapter?.clearList()
                    }
                }
                return true
            }

        })
        binding.ivRegresar.setOnClickListener {
            fragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }

    private fun initUi(provider: String) {
        binding.tvMensajeSearh.isVisible = true
        adapter = SearchAnimeAdapter() {
            Navigate.navigateActivities(
                it, requireContext(), EXTRA_ID,
                AnimeDetailActivity.EXTRA_PROVIDER, provider
            )
        }
        binding.rvSearchAnimes.setHasFixedSize(true)
        binding.rvSearchAnimes.layoutManager = GridLayoutManager(context, 3)
        binding.rvSearchAnimes.adapter = adapter
        searchViewModel.isloading.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

    }
    private fun backPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                fragmentManager?.beginTransaction()?.remove(this@SearchFragment)?.commit()
            }
        })
    }
    fun clearCache() {
        Log.i(ContentValues.TAG, "Clearing Cache.")
        val dir: Array<File> = requireContext().getCacheDir().listFiles() as Array<File>
        for (f in dir) {
            f.delete()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        clearCache()
    }

}