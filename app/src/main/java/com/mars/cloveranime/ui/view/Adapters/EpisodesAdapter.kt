package com.mars.cloveranime.ui.view.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.Episodes

class EpisodesAdapter(
    var listEpisodes: List<Episodes>,
    private val onItemSelect: (String) -> Unit,
) : RecyclerView.Adapter<EpisodesViewHolder>() {

    fun updateList(animeList: List<Episodes>) {
        this.listEpisodes = animeList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EpisodesViewHolder(layoutInflater.inflate(R.layout.item_episodes, parent, false))
    }

    override fun getItemCount(): Int = listEpisodes.size

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val item = listEpisodes[position]
        holder.bind(item, onItemSelect)
    }
}