package com.qoolqas.moviedb.ui.discover

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
import com.qoolqas.moviedb.utils.EndlessOnScrollListener
import kotlinx.android.synthetic.main.fragment_discover.*


class DiscoverFragment : Fragment() {

    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter
    private var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    var page: Int = 1

    var list = mutableListOf<DiscoverResultsItem>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        discoverRv.setHasFixedSize(true)
        discoverRv.layoutManager = linearLayoutManager
        setHasOptionsMenu(true)

        discoverPb.visibility = View.VISIBLE
        initRv()
        discoverViewModel = ViewModelProvider(this).get(DiscoverViewModel::class.java)
        discoverViewModel.init(1)
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
                    discoverViewModel.loadPopular(page)
                }
            }

        }
    }
}
