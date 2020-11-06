package com.qoolqas.moviedb.ui.discover

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem
import com.qoolqas.moviedb.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_card_discover.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class DiscoverAdapter(private val list: List<DiscoverResultsItem>) :
    RecyclerView.Adapter<DiscoverAdapter.ViewHolder>()  {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_discover, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.discoverTitle.text = list[position].title
        holder.view.discoverRatingStar.rating = list[position].voteAverage!!.toFloat()/2
        holder.view.discoverOverview.text = list[position].overview
        Glide.with(holder.view)
            .load("https://image.tmdb.org/t/p/w185" + list[position].posterPath)
            .placeholder(R.color.gray)
            .into(holder.view.discoverPoster)

//        val formatter = SimpleDateFormat("yyyy")
//        val newFormat: String = formatter.format(list[position].releaseDate)


        val outputFormat: DateFormat = SimpleDateFormat("yyyy", Locale.US)
        val inputFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date: Date = inputFormat.parse(list[position].releaseDate)
        val outputText: String = outputFormat.format(date)
        holder.view.discoverDate.text = outputText

        holder.view.setOnClickListener {
            val intentDetail = Intent(holder.view.context, DetailActivity::class.java)
            intentDetail.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intentDetail.putExtra(DetailActivity.EXTRA_ID, list[position].id)
            holder.view.context.startActivity(intentDetail)
            Log.d("id", list[position].id.toString())
        }
    }
}