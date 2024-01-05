package com.mars.cloveranime.ui.view.Adapters

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mars.cloveranime.R
import com.mars.cloveranime.data.SharedPreferencesCA.Companion.prefs
import com.mars.cloveranime.data.model.Episodes
import com.mars.cloveranime.databinding.ItemEpisodesBinding


class EpisodesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemEpisodesBinding.bind(view)

    fun bind(episode: Episodes, onItemSelect: (String) -> Unit) {
        binding.tvCapName.text = episode.animeName
        binding.tvNoCapitulo.text = episode.noEpisode
        Glide.with(binding.ivEpisode).load(episode.imageEpisode).skipMemoryCache(true)
            .into(binding.ivEpisode)
        binding.root.setOnClickListener {
            onItemSelect(episode.UrlEpisode)
            binding.ivVisto.setImageResource(R.drawable.ic_visto)
        }

        if (identifyProvider(episode.UrlEpisode)) {
            val episodeId = episode.UrlEpisode.replace(
                "https://monoschinos2.com/ver/",
                ""
            )
            watch(episodeId)
        } else {
            val episodeId = episode.UrlEpisode.replace(
                "https://www3.animeflv.net/ver/",
                ""
            )
            watch(episodeId)
        }


    }

    @SuppressLint("DiscouragedApi")
    private fun watch(episodeId: String) {
        binding.ivVisto.setImageResource(
            prefs.getWatchOption(episodeId)
        )
        binding.ivVisto.setOnClickListener {
            binding.ivVisto.setImageResource(R.drawable.ic_visto)
            prefs.saveWatchOption(episodeId, R.drawable.ic_visto)
        }
        binding.root.setOnLongClickListener {
            prefs.wipe(episodeId)
            binding.ivVisto.setImageResource(R.drawable.ic_no_visto)
            true
        }
    }

    private fun identifyProvider(id: String): Boolean {
        return id.contains("monoschinos2")
    }

}