package com.mars.cloveranime.ui.view.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mars.cloveranime.data.model.SearchAnimeModel
import com.mars.cloveranime.databinding.ItemSearchanimeBinding

class SearchAnimeViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val binding = ItemSearchanimeBinding.bind(view)
    fun bind(searchAnime: SearchAnimeModel, onItemselected: (String) -> Unit){
        binding.animeTittle.text = searchAnime.tittle
        binding.tvanimeType.text = searchAnime.type
        Glide.with(binding.ivAnimePortada).load(searchAnime.image).skipMemoryCache(true)
            .into(binding.ivAnimePortada)

        binding.root.setOnClickListener { onItemselected(searchAnime.AnimeUrl) }
    }
}