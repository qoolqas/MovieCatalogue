package com.qoolqas.moviedb.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.details.DetailsItem
import com.qoolqas.moviedb.model.details.DetailsMovieResponse
import com.qoolqas.moviedb.model.similiar.SimiliarResultsItem
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
    private val api: String = BuildConfig.API_KEY

    private var similiar = mutableListOf<SimiliarResultsItem>()

    private lateinit var similiarViewModel: SimiliarViewModel
    private lateinit var similiarAdapter: SimiliarAdapter
    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    companion object {
        public const val EXTRA_ID = "Extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra(EXTRA_ID, 0)
        Log.d("iddetail", id.toString())


        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)

        val collapsingToolbarLayout =
            findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)


        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.colorAccent)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.transparent)
        )



        Client().getApi().getDetails(id, api)
            .enqueue(object : retrofit2.Callback<DetailsMovieResponse> {
                override fun onFailure(call: Call<DetailsMovieResponse>, t: Throwable) {
                    Log.d("failure detail", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DetailsMovieResponse>,
                    response: Response<DetailsMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val respons: DetailsMovieResponse? = response.body()

                        detail_title.text = respons?.title

                        detail_description.text = respons?.overview
                        detail_rating_text.text = respons?.voteAverage.toString()

                        collapsingToolbarLayout.title = respons?.title
                        Glide.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w185" + respons?.posterPath)
                            .into(detail_poster)
                        Glide.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/original" + respons?.backdropPath)
                            .into(detail_backdrop)
                        detail_rating_star.rating = respons?.voteAverage!!.toFloat() / 2
                        var i = 0

                        for (item in respons.genres!!) {
                            detail_genre.text = detail_genre.text.toString() + item + " "
                            Log.d("Text", item.toString())

                        }


                    } else {
                        Log.d("else", "Failure")
                    }

                }

            })

        detail_similiarRv.setHasFixedSize(true)
        detail_similiarRv.layoutManager = linearLayoutManager

        similiarViewModel = ViewModelProviders.of(this).get(SimiliarViewModel::class.java)
        similiarViewModel.init(1, id)
        similiarViewModel.observerData(this, gotData())
        initRv()


    }

    private fun initRv() {
        similiarAdapter = SimiliarAdapter(similiar)
        detail_similiarRv.adapter = similiarAdapter
        Log.d("similiar", similiarAdapter.itemCount.toString())

    }

    private fun gotData(): Observer<MutableList<SimiliarResultsItem>> = Observer {
        similiar.clear()
        similiar.addAll(it)
        similiarAdapter.notifyDataSetChanged()
        Log.d("itsize", it.size.toString())
    }

}

