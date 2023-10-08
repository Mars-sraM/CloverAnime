package com.mars.cloveranime.ui.view.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.core.MylistDiffUtil
import com.mars.cloveranime.data.model.AnimeFavorite
import com.mars.cloveranime.data.model.DetailAnimeModelPart2
import com.mars.cloveranime.data.model.SearchAnimeModel
import java.util.Collections

class MylistAdapter(
    var resultAniemeList: List<AnimeFavorite>,
    private val onItemselected: (String) -> Unit
) :
    RecyclerView.Adapter<MylistViewHolder>() {
    fun updateList(newAnimeList: List<AnimeFavorite>) {
        val animeDiff = MylistDiffUtil(resultAniemeList, newAnimeList)
        val result = DiffUtil.calculateDiff(animeDiff)
        resultAniemeList = newAnimeList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MylistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MylistViewHolder(
            layoutInflater.inflate(
                R.layout.item_mylist,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = resultAniemeList.size

    override fun onBindViewHolder(holder: MylistViewHolder, position: Int) {
        val item = resultAniemeList[position]
        holder.bind(item, onItemselected)
    }
}