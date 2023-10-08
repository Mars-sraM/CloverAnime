package com.mars.cloveranime.ui.view.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mars.cloveranime.R
import com.mars.cloveranime.data.SharedPreferencesCA
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs
import com.mars.cloveranime.data.model.Episodes
import com.mars.cloveranime.databinding.ItemEpisodesBinding


class EpisodesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemEpisodesBinding.bind(view)

    fun bind(episode: Episodes, onItemSelect: (String) -> Unit){
        binding.tvCapName.text = episode.animeName
        binding.tvNoCapitulo.text = episode.noEpisode
        Glide.with(binding.ivEpisode).load(episode.imageEpisode).skipMemoryCache(true)
            .into(binding.ivEpisode)
        binding.root.setOnClickListener {
            onItemSelect(episode.UrlEpisode)
            binding.ivVisto.setImageResource(R.drawable.ic_visto)
        }
        if (identifyProvider(episode.UrlEpisode)){
            binding.ivVisto.setImageResource(
                prefs.getWatchOption(episode.UrlEpisode.replace("https://monoschinos2.com/ver/", ""))
            )
            binding.ivVisto.setOnClickListener {
                prefs.wipe(episode.UrlEpisode.replace("https://monoschinos2.com/ver/", ""))
                binding.ivVisto.setImageResource(R.drawable.ic_no_visto)
            }
        } else {
            binding.ivVisto.setImageResource(
                prefs.getWatchOption(episode.UrlEpisode.replace("https://www3.animeflv.net/ver/", ""))
            )
            binding.ivVisto.setOnClickListener {
                prefs.wipe(episode.UrlEpisode.replace("https://www3.animeflv.net/ver/", ""))
                binding.ivVisto.setImageResource(R.drawable.ic_no_visto)
            }
        }



    }
    private fun identifyProvider(id: String): Boolean {
        return id.contains("monoschinos2")
    }
}