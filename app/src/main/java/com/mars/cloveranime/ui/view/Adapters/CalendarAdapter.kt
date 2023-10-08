package com.mars.cloveranime.ui.view.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mars.cloveranime.R
import com.mars.cloveranime.data.model.CalendarModel
import com.mars.cloveranime.data.model.SearchAnimeModel

class CalendarAdapter(
    var list: List<CalendarModel> = emptyList(),
    private val onItemSelect: (String) -> Unit
): RecyclerView.Adapter<CalendarViewHolder>() {
    fun updateList(animeList: List<CalendarModel>) {
        this.list = animeList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CalendarViewHolder(layoutInflater.inflate(R.layout.item_calendar, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = list[position]
        holder.render(item, onItemSelect)
    }
}