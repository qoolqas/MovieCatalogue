package com.qoolqas.moviedb.ui

import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.details.DetailsMovieResponse
import com.qoolqas.moviedb.model.popular.PopularMovieResponse
import com.qoolqas.moviedb.model.popular.PopularResultsItem
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class DetailActivity : AppCompatActivity() {
    val api: String = BuildConfig.API_KEY
    val popularList: List<PopularResultsItem>? = null
    var ratingStar: RatingBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getStringExtra("id")

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

        Client().getApi().getDetails(api, id.toInt())
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
                        collapsingToolbarLayout.title(respons.title)


                    } else {
                        Log.d("else", "Failure")
                    }

                }

            })

    }
}

