package com.mars.cloveranime.ui.view.Adapters

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mars.cloveranime.R
import com.mars.cloveranime.data.SharedPreferencesCA
import com.mars.cloveranime.data.model.AnimeUrl
import com.mars.cloveranime.data.model.CapEmisionModel
import com.mars.cloveranime.databinding.ItemAnimeBinding

class EmisionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var binding = ItemAnimeBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(
        episode: CapEmisionModel,
        onItemSelect: (AnimeUrl) -> Unit

    ) {
        binding.animeTittle.text = episode.name
        binding.tvCapNumero.text = "Capitulo ${episode.capNunero}"
        Glide.with(binding.ivAnimePortada).load(episode.image).skipMemoryCache(true)
            .into(binding.ivAnimePortada)
        binding.tvAnimeType.text = episode.type

        binding.root.setOnClickListener {
            onItemSelect(episode.url)
        }
    }
}