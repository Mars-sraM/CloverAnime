package com.mars.cloveranime.ui.view.Adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mars.cloveranime.data.model.NewsModel
import com.mars.cloveranime.databinding.ItemNewsBinding

class NewsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var binding = ItemNewsBinding.bind(view)
    fun bind(news: NewsModel, onItemSelected: (String) -> Unit){
        binding.tvNewsType.text = "Anime"
        binding.tvNewsFecha.text = news.fecha
        binding.tvNewsTittle.text = news.name
        Glide.with(binding.ivNewsPortada).load(news.image).skipMemoryCache(true)
            .into(binding.ivNewsPortada)
        binding.root.setOnClickListener { onItemSelected(news.url) }
    }
}