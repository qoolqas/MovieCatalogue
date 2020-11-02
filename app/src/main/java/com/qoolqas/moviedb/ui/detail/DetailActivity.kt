package com.qoolqas.moviedb.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.credits.CastItem
import com.qoolqas.moviedb.model.details.DetailsMovieResponse
import com.qoolqas.moviedb.model.similiar.SimiliarResultsItem
import com.qoolqas.moviedb.ui.detail.adapter.CreditAdapter
import com.qoolqas.moviedb.ui.detail.adapter.SimiliarAdapter
import com.qoolqas.moviedb.ui.detail.viewmodel.CreditsViewModel
import com.qoolqas.moviedb.ui.detail.viewmodel.SimiliarViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Response
import java.util.*


class DetailActivity : AppCompatActivity() {
    private val api: String = BuildConfig.API_KEY

    private var similiar = mutableListOf<SimiliarResultsItem>()
    private var credit = mutableListOf<CastItem>()

    private lateinit var similiarViewModel: SimiliarViewModel
    private lateinit var creditsViewModel: CreditsViewModel

    private lateinit var similiarAdapter: SimiliarAdapter
    private lateinit var creditAdapter: CreditAdapter
    private var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    private var linearLayoutManagerCast: LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    private var appBarExpanded = true
    private var collapsedMenu: Menu? = null
    private var language = Locale.getDefault().toLanguageTag()



    companion object {
        const val EXTRA_ID = "Extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra(EXTRA_ID, 0)

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

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset -> //  Vertical offset == 0 indicates appBar is fully  expanded.
            if (kotlin.math.abs(verticalOffset) > 200) {
                appBarExpanded = false
                invalidateOptionsMenu()
            } else {
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        })


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
                                    if (BuildConfig.DEBUG && detail_backdrop == null) {
                                        error("Assertion failed")
                                    }
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

                                            val mutedColor = palette.getVibrantColor(R.attr.colorPrimary)
                                            collapsingToolbarLayout.setContentScrimColor(mutedColor)
                                        })
                                }

                                override fun onBitmapFailed(errorDrawable: Drawable?) {

                                }
                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                                }
                            })

                        detail_rating_star.rating = respons?.voteAverage!!.toFloat() / 2
                        val i = 0

                        for (item in respons.genres?.get(i)?.name!!) {
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

        detail_cast_rv.setHasFixedSize(true)
        detail_cast_rv.layoutManager = linearLayoutManagerCast

        creditsViewModel = ViewModelProviders.of(this).get(CreditsViewModel::class.java)
        creditsViewModel.init(language, id)
        creditsViewModel.observerData(this, gotDataCredits())
        initRv()


    }

    private fun initRv() {
        similiarAdapter = SimiliarAdapter(similiar)
        creditAdapter = CreditAdapter(credit)
        detail_similiarRv.adapter = similiarAdapter
        detail_cast_rv.adapter = creditAdapter

    }

    private fun gotData(): Observer<MutableList<SimiliarResultsItem>> = Observer {
        similiar.clear()
        similiar.addAll(it)
        similiarAdapter.notifyDataSetChanged()
        detail_pbrv.visibility = View.GONE
        Log.d("itsize", it.size.toString())
    }
    private fun gotDataCredits(): Observer<MutableList<CastItem>> = Observer {
        credit.clear()
        credit.addAll(it)
        creditAdapter.notifyDataSetChanged()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (collapsedMenu != null
            && (!appBarExpanded || collapsedMenu!!.size() !== 1)
        ) {
            //collapsed
            collapsedMenu!!.add("Add")
                .setIcon(R.drawable.ic_favorite_black_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        } else {
            //expanded
        }
        return super.onPrepareOptionsMenu(collapsedMenu)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        collapsedMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_settings -> return true
        }
        if (item.title === "Add") {
            Toast.makeText(this, "clicked add", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}

