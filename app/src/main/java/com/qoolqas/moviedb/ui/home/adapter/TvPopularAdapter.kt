package com.qoolqas.moviedb.ui.home.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.popular.PopularResultsItem
import com.qoolqas.moviedb.model.tv.TvPopularItem
import com.qoolqas.moviedb.ui.detail.DetailActivity
import com.qoolqas.moviedb.ui.detail.DetailActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.item_card_popular.view.*
import kotlinx.android.synthetic.main.item_card_tv.view.*

class TvPopularAdapter(private val list: List<TvPopularItem>) :
    RecyclerView.Adapter<TvPopularAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_tv, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tv_popular_name.text = list[position].name
        Glide.with(holder.view)
            .load("https://image.tmdb.org/t/p/w500" + list[position].backdropPath)
            .placeholder(R.color.gray)
            .into(holder.view.tv_popular_backdrop)

        holder.view.setOnClickListener {
            val intentDetail = Intent(holder.view.context, DetailActivity::class.java)
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intentDetail.putExtra(EXTRA_ID, list[position].id)
            holder.view.context.startActivity(intentDetail)
            Log.d("id", list[position].id.toString())
        }
    }

}