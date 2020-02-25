package com.qoolqas.moviedb.ui.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.PopularMovieItem
import kotlinx.android.synthetic.main.card_movie.view.*

class PopularAdapter(private val list: List<PopularMovieItem>) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_movie,parent,false))
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.tvTitle.text = list.get(position).title
        Glide.with(holder.view)
            .load("https://image.tmdb.org/t/p/w185" + list.get(position).poster_path)
            .into(holder.view.imgPoster)
    }

}