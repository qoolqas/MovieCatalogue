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
import androidx.lifecycle.ViewModelProvider
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
import com.qoolqas.moviedb.model.tvdetails.TvDetailResponse
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
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Suppress(
    "NAME_SHADOWING", "DEPRECATED_IDENTITY_EQUALS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)
class DetailActivity : AppCompatActivity() {
    private val api: String = BuildConfig.API_KEY

    private var similiar = mutableListOf<SimiliarResultsItem>()
    private var credit = mutableListOf<CastItem>()

    private lateinit var similiarViewModel: SimiliarViewModel
    private lateinit var creditsViewModel: CreditsViewModel

    private lateinit var similiarAdapter: SimiliarAdapter
    private lateinit var creditAdapter: CreditAdapter
    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    private var linearLayoutManagerCast: LinearLayoutManager =
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    private var appBarExpanded = true
    private var collapsedMenu: Menu? = null
    private var language = Locale.getDefault().toLanguageTag()
    var id: Int = 0
    private var code: String = ""


    companion object {
        const val EXTRA_ID = "Extra"
        const val EXTRA_CODE = "Extra_Code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        id = intent.getIntExtra(EXTRA_ID, 0)
        code = intent.getStringExtra(EXTRA_CODE)
        val toolbar = findViewById<Toolbar>(R.id.toolbar1)
        setSupportActionBar(toolbar)

//        val collapsingToolbarLayout =
//            findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)

        collapsing_toolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.colorAccent)
        )
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.transparent)
        )
        detail_pbrv.visibility = View.VISIBLE

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset -> //  Vertical offset == 0 indicates appBar is fully  expanded.
            if (kotlin.math.abs(verticalOffset) > 200) {
                appBarExpanded = false
                invalidateOptionsMenu()
            } else {
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        })
        if (code == "tv"){
            getDetailTv()
        }else{
            getDetailMovie()
        }
        initRv()


    }
    private fun getDetailMovie() {
        Client().getApi().getDetails(id, api, language)
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

                        val timeSec: Long = respons?.runtime!!.toLong() // Json output
                        Log.d("asd123", timeSec.toString())

                        val hours = timeSec.toInt() / 3600
                        var temp = timeSec.toInt() - hours * 3600
                        val mins = temp / 60
                        temp -= mins * 60
                        val secs = temp

                        val requiredFormat =
                            "$mins Hours $secs Minute"
                        detail_runtime.text = "Duration : $requiredFormat"
                        val outputFormat: DateFormat = SimpleDateFormat("yyyy", Locale.US)
                        val inputFormat: DateFormat =
                            SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        val date: Date = inputFormat.parse(respons.releaseDate)
                        Log.d("date123", respons.releaseDate)
                        val outputText: String = outputFormat.format(date)
                        detail_title.text = respons.title + " ($outputText)"

                        detail_description.text = respons.overview
                        detail_rating_text.text = respons.voteAverage.toString()

                        collapsing_toolbar.title = respons.title
                        Glide.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w185" + respons.posterPath)
                            .into(detail_poster)

                        Picasso.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w500" + respons.backdropPath)
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

                                            val mutedColor =
                                                palette.getVibrantColor(R.attr.colorPrimary)
                                            collapsing_toolbar.setContentScrimColor(mutedColor)
                                        })
                                }

                                override fun onBitmapFailed(errorDrawable: Drawable?) {

                                }

                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                                }
                            })

                        detail_rating_star.rating = respons.voteAverage!!.toFloat() / 2
                        for (i in respons.genres?.indices!!) {
                            println(respons.genres[i])
                            detail_genre.text =
                                detail_genre.text.toString() + respons.genres[i].name + ", "
                            Log.d("Text", respons.genres[i].name.toString())
                        }

                    } else {
                        Log.d("else", "Failure")
                    }

                }

            })
        detail_similiarRv.setHasFixedSize(true)
        detail_similiarRv.layoutManager = linearLayoutManager

        similiarViewModel = ViewModelProvider(this).get(SimiliarViewModel::class.java)
        similiarViewModel.init(1, id)
        similiarViewModel.observerData(this, gotData())

        detail_cast_rv.setHasFixedSize(true)
        detail_cast_rv.layoutManager = linearLayoutManagerCast

        creditsViewModel = ViewModelProvider(this).get(CreditsViewModel::class.java)
        creditsViewModel.init(language, id)
        creditsViewModel.observerData(this, gotDataCredits())
    }
    private fun getDetailTv(){
        Client().getApi().getDetailsTv(id, api, language)
            .enqueue(object : retrofit2.Callback<TvDetailResponse> {
                override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                    Log.d("failure detail", t.message.toString())
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<TvDetailResponse>,
                    response: Response<TvDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val respons: TvDetailResponse? = response.body()
                        val runtime = respons!!.episodeRunTime
                        detail_runtime.text = "Duration : $runtime Minute"

//                        val outputFormat: DateFormat = SimpleDateFormat("yyyy", Locale.US)
//                        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
//                        val date: Date = inputFormat.parse(respons.firstAirDate)
//                        val outputText: String = outputFormat.format(date)
//                        detail_title.text = respons.title + " ($outputText)"

                        val outputFormat: DateFormat = SimpleDateFormat("yyyy", Locale.US)
                        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                        Log.d("date12", respons.firstAirDate + respons.lastAirDate)
                        val date: Date = inputFormat.parse(respons.firstAirDate)
                        val dateLast: Date = inputFormat.parse(respons.lastAirDate)
                        val outputText: String = outputFormat.format(date)
                        detail_title.text = respons.name + " ($outputText)"

                        detail_description.text = respons.overview
                        detail_rating_text.text = respons.voteAverage.toString()

                        collapsing_toolbar.title = respons.name
                        Glide.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w185" + respons.posterPath)
                            .into(detail_poster)

                        Picasso.with(this@DetailActivity)
                            .load("https://image.tmdb.org/t/p/w500" + respons.backdropPath)
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

                                            val mutedColor =
                                                palette.getVibrantColor(R.attr.colorPrimary)
                                            collapsing_toolbar.setContentScrimColor(mutedColor)
                                        })
                                }

                                override fun onBitmapFailed(errorDrawable: Drawable?) {

                                }

                                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                                }
                            })

                        detail_rating_star.rating = respons.voteAverage!!.toFloat() / 2
                        for (i in respons.genres?.indices!!) {
                            println(respons.genres[i])
                            detail_genre.text =
                                detail_genre.text.toString() + respons.genres[i].name + ", "
                            Log.d("Text", respons.genres[i].name.toString())
                        }

                    } else {
                        Log.d("else", "Failure")
                    }

                }

            })
        detail_similiarRv.setHasFixedSize(true)
        detail_similiarRv.layoutManager = linearLayoutManager

        similiarViewModel = ViewModelProvider(this).get(SimiliarViewModel::class.java)
        similiarViewModel.init(1, id)
        similiarViewModel.observerData(this, gotData())

        detail_cast_rv.setHasFixedSize(true)
        detail_cast_rv.layoutManager = linearLayoutManagerCast

        creditsViewModel = ViewModelProvider(this).get(CreditsViewModel::class.java)
        creditsViewModel.init(language, id)
        creditsViewModel.observerData(this, gotDataCredits())
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
        if (it.size == 0) {
            similiar_txt.visibility = View.GONE
        }
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

