package com.mars.cloveranime.ui.view.Adapters

import android.view.View
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.databinding.ItemMylistBinding
import com.mars.cloveranime.databinding.ItemSearchanimeBinding
import java.util.UUID

class MylistViewHolder (view: View): RecyclerView.ViewHolder(view) {

    val binding = ItemMylistBinding.bind(view)

    fun bind(anime: AnimeFavorite, onItemselected: (String) -> Unit){
        val url = finalUrl(anime.animeUrl)
        binding.animeTittle.text = anime.animeName
        binding.tvanimeProvider.text = if(identifyProvider(anime.animeUrl)){
            "MonosChinos" } else { "AnimeFLV" }

        Glide.with(binding.ivAnimePortada).load(imgLength(anime.animeimg, anime.animeUrl)).skipMemoryCache(true)
            .into(binding.ivAnimePortada)

        binding.root.setOnClickListener { onItemselected(url) }
    }
    private fun finalUrl(id: String): String {
        val url = if (identifyProvider(id)){
            "https://monoschinos2.com/anime/$id"
        } else {
            "https://www3.animeflv.net/anime/$id"
        }
        return  url
    }
    private fun imgLength(urlImage: String, id: String): String {
        var fullUrlImage = ""
        if (urlImage.length < 12) {
            fullUrlImage = "https://www3.animeflv.net/uploads/animes/covers/$urlImage"
        } else {
            fullUrlImage = "https://monoschinos2.com/thumbs/imagen/$urlImage"
        }
        return fullUrlImage
    }
    private fun identifyProvider(id: String): Boolean {
        return id.contains("-sub-espanol")
    }
}