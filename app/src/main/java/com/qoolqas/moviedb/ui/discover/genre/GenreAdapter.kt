package com.qoolqas.moviedb.ui.discover.genre

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.genrestatic.StaticData
import kotlinx.android.synthetic.main.item_card_genre.view.*

class GenreAdapter(private val list: List<StaticData>) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>()  {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_genre, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.genre_name.text = list[position].name
        Glide.with(holder.view)
            .load(list[position].image)
            .placeholder(R.color.gray)
            .into(holder.view.genre_background)

        holder.view.setOnClickListener {
            val action = GenreFragmentDirections.actionNavGenreToNavSearchGenre(list[position])
            Navigation.findNavController(it).navigate(action)
            Log.d("id", list[position].id.toString())
        }
    }

}