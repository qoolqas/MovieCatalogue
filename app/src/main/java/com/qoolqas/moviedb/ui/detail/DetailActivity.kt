package com.qoolqas.moviedb.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.PaletteAsyncListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.details.DetailsMovieResponse
import com.qoolqas.moviedb.model.similiar.SimiliarResultsItem
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_detail.*
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
        detail_pbrv.visibility = View.VISIBLE



        Client().getApi().getDetails(id, api)
            .enqueue(object : retrofit2.Callback<DetailsMovieResponse> {
                override fun onFailure(call: Call<DetailsMovieResponse>, t: Throwable) {
                    Log.d("failure detail", t.message.toString())
                }

                @SuppressLint("SetTextI18n")
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
//                        Glide.with(this@DetailActivity)
//                            .load("https://image.tmdb.org/t/p/original" + respons?.backdropPath)
//                            .into(detail_backdrop)


                        Picasso.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w500" + respons?.backdropPath)
                            .into(object : Target {
                                override fun onBitmapLoaded(
                                    bitmap: Bitmap?,
                                    from: LoadedFrom?
                                ) {
                                    assert(detail_backdrop != null)
                                    detail_backdrop.setImageBitmap(bitmap)
                                    Palette.from(bitmap!!)
                                        .generate(PaletteAsyncListener { palette ->
                                            val textSwatch = palette!!.vibrantSwatch
                                            if (textSwatch == null) {
                                                Toast.makeText(
                                                    this@DetailActivity,
                                                    "Null swatch :(",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                return@PaletteAsyncListener
                                            }
//                                            backgroundGroup.setBackgroundColor(textSwatch.rgb)
//                                            titleColorText.setTextColor(textSwatch.titleTextColor)
//                                            bodyColorText.setTextColor(textSwatch.bodyTextColor)
                                            val mutedColor = palette!!.getMutedColor(R.attr.colorPrimary)
                                            collapsingToolbarLayout.setContentScrimColor(mutedColor)
                                        })
                                }

                                override fun onBitmapFailed(errorDrawable: Drawable?) {

                                }
                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                                }
                            })

                        detail_rating_star.rating = respons?.voteAverage!!.toFloat() / 2
                        var i = 0

                        for (item in respons.genres?.get(i)?.name!!) {
                            detail_genre.text = detail_genre.text.toString() + item + " "
                            Log.d("Text", item.toString())

                        }

//                        val rh: Int = getResources()
//                            .getIdentifier(detail_backdrop, "drawable", getPackageName())
//                        val bitmapPalette = BitmapFactory.decodeResource(
//                            resources,
//                            R.drawable.exposter
//                        )
//                        Palette.from(bitmapPalette).generate { palette ->
//                            val mutedColor = palette!!.getMutedColor(R.attr.colorPrimary)
//                            collapsingToolbarLayout.setContentScrimColor(mutedColor)
//                        }


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
        detail_pbrv.visibility = View.GONE
        Log.d("itsize", it.size.toString())
    }

}

