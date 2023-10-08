package com.mars.cloveranime.ui.view.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.data.model.DetailGenres
import com.mars.cloveranime.databinding.ItemCategoriesBinding

class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var binding = ItemCategoriesBinding.bind(view)

    fun bind(animeGeneros: DetailGenres){
        binding.tvAnimeCategory.text = animeGeneros.name
    }
}