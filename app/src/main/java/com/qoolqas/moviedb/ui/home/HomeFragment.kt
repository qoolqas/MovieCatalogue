package com.qoolqas.moviedb.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.ui.home.adapter.NowPlayingAdapter
import com.qoolqas.moviedb.ui.home.adapter.PopularAdapter
import com.qoolqas.moviedb.ui.home.viewmodel.NowPlayingViewModel
import com.qoolqas.moviedb.ui.home.viewmodel.PopularViewModel
import com.qoolqas.moviedb.utils.LinePagerIndicatorDecoration
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var nowPlayingViewModel: NowPlayingViewModel

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null
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
        getPopular()
        getNowPlaying()


    }
    fun getPopular(){
        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel::class.java)
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


    fun getNowPlaying(){
        nowPlayingViewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)
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
