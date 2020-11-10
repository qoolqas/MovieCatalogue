package com.qoolqas.moviedb.ui.home.homenav

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.nowplaying.NowPlayingResultsItem
import com.qoolqas.moviedb.ui.detail.DetailActivity
import com.qoolqas.moviedb.ui.detail.DetailActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.item_pager_main.view.*

class HomeNavAdapter(private val list: List<NowPlayingResultsItem>) :
    RecyclerView.Adapter<HomeNavAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_homenav, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.pagerTitle.text = list[position].title
        Glide.with(holder.view)
            .load("https://image.tmdb.org/t/p/w500" + list[position].backdropPath)
            .placeholder(R.color.gray)
            .into(holder.view.pagerImage)

        holder.view.setOnClickListener {
            when(holder.adapterPosition){
                0 -> Log.d("when0", "when0")
                1 -> Log.d("when1", "when1")
                2 -> Log.d("when2", "when2")
                3 -> Log.d("when3", "when3")
            }
//            val intentDetail = Intent(holder.view.context, DetailActivity::class.java)
//            intentDetail.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
//            intentDetail.putExtra(EXTRA_ID, list[position].id)
//            holder.view.context.startActivity(intentDetail)
        }
    }


}