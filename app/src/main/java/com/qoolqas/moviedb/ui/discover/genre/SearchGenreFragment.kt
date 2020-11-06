package com.qoolqas.moviedb.ui.discover.genre

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem
import com.qoolqas.moviedb.ui.discover.DiscoverAdapter
import com.qoolqas.moviedb.ui.discover.DiscoverViewModel
import com.qoolqas.moviedb.utils.EndlessOnScrollListener
import kotlinx.android.synthetic.main.fragment_discover.*
import java.util.*

class SearchGenreFragment : Fragment() {
    private lateinit var discoverViewModel: SearchGenreViewModel
    private lateinit var discoverAdapter: DiscoverAdapter
    private var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    var page: Int = 1
    private lateinit var genreids : String
    private var language = Locale.getDefault().toLanguageTag()

    var list = mutableListOf<DiscoverResultsItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = SearchGenreFragmentArgs.fromBundle(this.arguments!!)
        genreids = args.genreId?.id.toString()
        Log.d("Args", genreids)

        discoverRv.setHasFixedSize(true)
        discoverRv.layoutManager = linearLayoutManager
        setHasOptionsMenu(true)
        initRv()
        discoverViewModel = ViewModelProviders.of(this).get(SearchGenreViewModel::class.java)
        discoverViewModel.init(1,language,genreids)
        discoverViewModel.observerData(this,gotData())

        scrollData()
    }

    private fun gotData(): Observer<MutableList<DiscoverResultsItem>> = Observer {
        list.clear()
        list.addAll(it)
        discoverAdapter.notifyDataSetChanged()
        discoverPb?.visibility = View.GONE
    }
    private fun initRv() {
        discoverAdapter = DiscoverAdapter(list)
        discoverRv?.adapter = discoverAdapter
        discoverAdapter.notifyDataSetChanged()
        scrollData()?.let {
            discoverRv?.addOnScrollListener(it)
        }
    }
    private fun scrollData(): EndlessOnScrollListener? {
        return object : EndlessOnScrollListener() {

            override fun onLoadMore() {
                if (list.isNotEmpty()) {
                    Log.d("loadMore", "lof")
                    page += 1
                    discoverViewModel.loadDiscover(page,language,genreids)
                }
            }

        }
    }
}