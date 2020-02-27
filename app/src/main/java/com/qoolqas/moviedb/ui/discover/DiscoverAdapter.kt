package com.qoolqas.moviedb.ui.discover

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem
import kotlinx.android.synthetic.main.item_card_discover.view.*
import kotlinx.android.synthetic.main.item_card_popular.view.*

class DiscoverAdapter(private val list: List<DiscoverResultsItem>) :
    RecyclerView.Adapter<DiscoverAdapter.ViewHolder>()  {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_discover, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.discoverTitle.text = list.get(position).title
        Glide.with(holder.view)
            .load("https://image.tmdb.org/t/p/w185" + list.get(position).posterPath)
            .into(holder.view.discoverPoster)
    }
}