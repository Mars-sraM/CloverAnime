package com.mars.cloveranime.core

import androidx.recyclerview.widget.DiffUtil
import com.mars.cloveranime.data.model.AnimeFavorite
import java.util.Collections

class MylistDiffUtil(
    private val oldList:List<AnimeFavorite>,
    private val newList: List<AnimeFavorite>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].animeUrl == newList[newItemPosition].animeUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == newList[newItemPosition]
    }

}