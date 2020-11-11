package com.qoolqas.moviedb.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.genrestatic.StaticData
import kotlinx.android.synthetic.main.item_homenav.view.*

class HomeNavAdapter(private val list: List<StaticData>) :
    RecyclerView.Adapter<HomeNavAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_homenav, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.homenav_name.text = list[position].name
        Glide.with(holder.view)
            .load(list[position].image)
            .placeholder(R.color.gray)
            .into(holder.view.homenav_image)

        holder.view.setOnClickListener {
            when(holder.adapterPosition){
                0 -> Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_discover, null)
                1 -> Log.d("when1", "when1")
                2 -> Log.d("when2", "when2")
                3 -> Log.d("when3", "when3")
                4 -> Navigation.createNavigateOnClickListener(R.id.action_nav_home_to_nav_genre, null)
                5 -> Log.d("when3", "when3")
                6 -> Log.d("when3", "when3")
                7 -> Log.d("when3", "when3")
            }
        }
    }
}