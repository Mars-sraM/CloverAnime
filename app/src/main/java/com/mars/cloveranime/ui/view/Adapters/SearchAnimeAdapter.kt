package com.mars.cloveranime.ui.view.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.SearchAnimeModel

class SearchAnimeAdapter(
    var resultAniemeList: List<SearchAnimeModel> = emptyList(),
    private val onItemselected: (String) -> Unit
) :
    RecyclerView.Adapter<SearchAnimeViewHolder>() {

    fun updateList(animeList: List<SearchAnimeModel>) {
        this.resultAniemeList = animeList
        notifyDataSetChanged()
    }
    fun clearList() {
        val size: Int = resultAniemeList.size
        resultAniemeList.toMutableList().clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAnimeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchAnimeViewHolder(
            layoutInflater.inflate(
                R.layout.item_searchanime,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = resultAniemeList.size

    override fun onBindViewHolder(holder: SearchAnimeViewHolder, position: Int) {
        val item = resultAniemeList[position]
        holder.bind(item, onItemselected)
    }
}