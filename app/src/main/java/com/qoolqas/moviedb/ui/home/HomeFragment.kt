package com.qoolqas.moviedb.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.genrestatic.StaticData
import com.qoolqas.moviedb.ui.discover.genre.GenreAdapter
import com.qoolqas.moviedb.ui.home.adapter.NowPlayingAdapter
import com.qoolqas.moviedb.ui.home.adapter.PopularAdapter
import com.qoolqas.moviedb.ui.home.adapter.TvPopularAdapter
import com.qoolqas.moviedb.ui.home.homenav.HomeNavAdapter
import com.qoolqas.moviedb.ui.home.viewmodel.NowPlayingViewModel
import com.qoolqas.moviedb.ui.home.viewmodel.PopularViewModel
import com.qoolqas.moviedb.ui.home.viewmodel.TvShowViewModel
import com.qoolqas.moviedb.utils.LinePagerIndicatorDecoration
import kotlinx.android.synthetic.main.fragment_genre.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var nowPlayingViewModel: NowPlayingViewModel
    private lateinit var tvShowViewModel: TvShowViewModel

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
    private var language = Locale.getDefault().toLanguageTag()

    private val genreData = listOf(
        StaticData(R.drawable.exposter, "Discover",28),
        StaticData(R.drawable.exbackdrop, "Popular",12),
        StaticData(R.drawable.exposter, "Tv Show",16),
        StaticData(R.drawable.exbackdrop, "Series",35),
        StaticData(R.drawable.exposter, "Genre",80),
        StaticData(R.drawable.exbackdrop, "Country",99),
        StaticData(R.drawable.exposter, "Favorite",18),
        StaticData(R.drawable.exbackdrop, "Advanced Search",10751)
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        popularPb.visibility = View.VISIBLE
        popularDivider.visibility = View.GONE
        homeNav()
        getPopular()
        getNowPlaying()
        getTv()


    }
    private fun homeNav(){
        homenav_rv.apply {
            homenav_rv.layoutManager = GridLayoutManager(context, 4)
            adapter = HomeNavAdapter(genreData)
        }
    }
    private fun getPopular(){
        popularViewModel = ViewModelProvider(this).get(PopularViewModel::class.java)
        popularViewModel.init(1)
        popularViewModel.livePopular().observe(viewLifecycleOwner, Observer { popular ->
            popularRv.apply {
                popularRv.setHasFixedSize(true)
                popularRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = PopularAdapter(popular)
            }
            popularPb.visibility = View.GONE
            popularDivider.visibility = View.VISIBLE

        })
    }
    private fun getTv(){
        tvShowViewModel = ViewModelProvider(this).get(TvShowViewModel::class.java)
        tvShowViewModel.init(1,language)
        tvShowViewModel.livePopular().observe(viewLifecycleOwner, Observer { popular ->
            tv_popular_rv.apply {
                tv_popular_rv.setHasFixedSize(true)
                tv_popular_rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = TvPopularAdapter(popular)
            }
        })
    }


    private fun getNowPlaying(){
        nowPlayingViewModel = ViewModelProvider(this).get(NowPlayingViewModel::class.java)
        nowPlayingViewModel.init(1)
        nowPlayingViewModel.liveNowPlaying().observe(viewLifecycleOwner, Observer { nowPlaying ->
            rvPager.apply {
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(rvPager)
                rvPager.onFlingListener = null
                rvPager.addItemDecoration(LinePagerIndicatorDecoration())
                rvPager.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = NowPlayingAdapter(nowPlaying)
            }
            popularPb.visibility = View.GONE
            popularDivider.visibility = View.VISIBLE
        })
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager =
            activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }
        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    Log.i("onQueryTextChange", newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    val action = HomeFragmentDirections.actionNavHomeToNavSearch(query)
                    Navigation.findNavController(view!!).navigate(action)

                    val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(
                        searchView!!.windowToken,
                        InputMethodManager.RESULT_UNCHANGED_SHOWN
                    )

                    Log.i("onQueryTextSubmit", query)
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                // Not implemented here
                return false
            else -> {
            }
        }
        searchView!!.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

}
