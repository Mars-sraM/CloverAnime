package com.mars.cloveranime.ui.view.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.databinding.ItemCalendarBinding

class CalendarViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var binding = ItemCalendarBinding.bind(view)
    fun render(anime: CalendarModel, onItemSelect: (String) -> Unit) {
        binding.animeTittle.text = anime.animeName
        binding.tvanimeProvider.text = anime.provider
        binding.animeSynopsis.text = anime.animeSynapsis

        Glide.with(binding.ivAnimePortada).load(anime.animeImg).skipMemoryCache(true)
            .into(binding.ivAnimePortada)


        binding.root.setOnClickListener {
            onItemSelect(anime.animeUrl)
        }

    }
}