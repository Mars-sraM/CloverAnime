package com.mars.cloveranime.ui.view.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.DetailGenres

class CategoryAdapter(val categotyList: List<DetailGenres>): RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(layoutInflater.inflate(R.layout.item_categories, parent, false))
    }

    override fun getItemCount(): Int = categotyList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = categotyList[position]
        holder.bind(item)
    }
}