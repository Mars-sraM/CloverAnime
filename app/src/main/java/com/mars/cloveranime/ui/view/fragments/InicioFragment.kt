package com.mars.cloveranime.ui.view.fragments

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mars.cloveranime.R
import com.mars.cloveranime.databinding.FragmentInicioBinding
import com.mars.cloveranime.ui.view.Adapters.NewsAdapter
import com.mars.cloveranime.ui.view.MainActivity
import com.mars.cloveranime.ui.viewModel.NewsViewModel
import com.mars.cloveranime.ui.viewModel.PreferencesViewModel
import java.io.File


class InicioFragment : Fragment() {
    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel: NewsViewModel by viewModels()
    private val prefsViewModel: PreferencesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.addListNews()
        newsViewModel.newsViewModel.observe(viewLifecycleOwner, Observer { news ->
            if (!news.isNullOrEmpty()){
                binding.ivError.isVisible = false
                val adapter = NewsAdapter(news) {itemSelected(it)}
                binding.rvNews.setHasFixedSize(true)
                binding.rvNews.layoutManager = LinearLayoutManager(context)
                binding.rvNews.adapter = adapter
            }
            newsViewModel.isloading.observe(viewLifecycleOwner, Observer {
                binding.rvNewsLoading.isVisible = !it
                binding.rvNews.isVisible = it
                binding.ivError.isVisible = if (news.isNullOrEmpty()){ it }else{ false }
            })
        })

    }

    private fun itemSelected(url: String) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
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