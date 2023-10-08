package com.mars.cloveranime.ui.view.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.core.MylistDiffUtil
import com.mars.cloveranime.data.model.AnimeUrl
import com.mars.cloveranime.data.model.CapEmisionModel
import com.mars.cloveranime.data.model.SearchAnimeModel

class EmisionAdapter(
    private var animesList: MutableList<CapEmisionModel>,
    private val onItemSelect: (AnimeUrl) -> Unit,
    //private val onItemAnimeSelect: (String) -> Unit
) : RecyclerView.Adapter<EmisionViewHolder>() {
    fun updateList(animeList: List<CapEmisionModel>) {
        this.animesList = animeList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmisionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EmisionViewHolder(layoutInflater.inflate(R.layout.item_anime, parent, false))
    }

    override fun getItemCount(): Int = animesList.size

    override fun onBindViewHolder(holder: EmisionViewHolder, position: Int) {
        val item = animesList[position]
        holder.bind(item, onItemSelect)
    }
}