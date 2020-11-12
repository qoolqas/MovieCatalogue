package com.qoolqas.moviedb.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem
import com.qoolqas.moviedb.ui.discover.DiscoverAdapter
import com.qoolqas.moviedb.utils.EndlessOnScrollListener
import kotlinx.android.synthetic.main.fragment_search_movie.*
import java.util.*

class SearchMovieFragment : Fragment() {
    private lateinit var searchMovieViewModel : SearchMovieViewModel
    private lateinit var discoverAdapter: DiscoverAdapter
    private var language = Locale.getDefault().toLanguageTag()
    var page: Int = 1
    lateinit var query : String

    var list = mutableListOf<DiscoverResultsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        query = arguments?.getString("searchData").toString()
        Log.d("Args", query)
        searchRv.setHasFixedSize(true)
        searchRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        setHasOptionsMenu(true)

        initRv()
        searchMovieViewModel = ViewModelProvider(this).get(SearchMovieViewModel::class.java)
        searchMovieViewModel.init(language,query,page)
        searchMovieViewModel.observerData(this,gotData())

        scrollData()
    }
    private fun gotData(): Observer<MutableList<DiscoverResultsItem>> = Observer {
        list.clear()
        list.addAll(it)
        discoverAdapter.notifyDataSetChanged()
    }
    private fun initRv() {
        discoverAdapter = DiscoverAdapter(list)
        searchRv.adapter = discoverAdapter
        discoverAdapter.notifyDataSetChanged()
        scrollData()?.let {
            searchRv.addOnScrollListener(it)
        }
    }

    private fun scrollData(): EndlessOnScrollListener? {
        return object : EndlessOnScrollListener() {
            override fun onLoadMore() {
                if (list.isNotEmpty()) {
                    Log.d("loadMore", "lof")
                    page += 1
                    searchMovieViewModel.loadPopular(language,query,page)
                }
            }

        }
    }
}