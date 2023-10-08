package com.mars.cloveranime.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.mars.cloveranime.R
import com.mars.cloveranime.core.CheckInternet
import com.mars.cloveranime.core.Navigate
import com.mars.cloveranime.databinding.FragmentFavoriteBinding
import com.mars.cloveranime.ui.view.Adapters.MylistAdapter
import com.mars.cloveranime.ui.view.AnimeDetailActivity
import com.mars.cloveranime.ui.viewModel.AnimeFavoriteModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Collections

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var adapter: MylistAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoritesViewModel: AnimeFavoriteModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //.toSet()
        val internet = CheckInternet.isInternetOn(requireContext())
        favoritesViewModel.getAnimes(requireContext())
        favoritesViewModel.animesFavorites.observe(viewLifecycleOwner, Observer { animes ->
            adapter = MylistAdapter(animes){
                if (internet) {
                    Navigate.navigateActivities(it, requireContext(),
                        AnimeDetailActivity.EXTRA_ID, AnimeDetailActivity.EXTRA_PROVIDER, identifyProvider(it)
                    )
                } else {
                    Toast.makeText(requireContext(), "No hay acceso a internet", Toast.LENGTH_SHORT).show()
                }
            }

            val layoutManager = GridLayoutManager(context, 2)
            binding.rvAnimesFavorites.setHasFixedSize(true)
            binding.rvAnimesFavorites.layoutManager = layoutManager
            binding.rvAnimesFavorites.adapter = adapter
        })

    }

    private fun identifyProvider(url: String): String {
        val animeProvider = if (url.contains("https://monoschinos2.com")){
            "MonosChinos"
        } else {
            "AnimeFLV"
        }
        return animeProvider
    }

    override fun onResume() {
        super.onResume()
        favoritesViewModel.getAnimes(requireContext())
        favoritesViewModel.animesFavorites.observe(viewLifecycleOwner, Observer { animes ->
            if(animes.size != adapter.itemCount){
                adapter.updateList(animes)
            }
        })
    }


}