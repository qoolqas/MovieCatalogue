package com.qoolqas.moviedb.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem

class DiscoverFragment : Fragment() {

    private lateinit var discoverViewModel: DiscoverViewModel
    private lateinit var discoverAdapter: DiscoverAdapter
    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    private var discoverRv: RecyclerView? = null
    private var discoverPb: ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_discover, container, false)
        discoverRv = v.findViewById(R.id.discoverRv)
        discoverPb = v.findViewById(R.id.discoverPb)
        discoverRv?.setHasFixedSize(true)
        discoverRv?.layoutManager = linearLayoutManager
        setHasOptionsMenu(true)
        discoverPb?.visibility = View.VISIBLE

        discoverViewModel = ViewModelProviders.of(this).get(DiscoverViewModel::class.java)
        discoverViewModel.init(1)
        discoverViewModel.livePopular().observe(viewLifecycleOwner, Observer { discover ->
            initRv(discover)
            discoverPb?.visibility = View.GONE

        })

        return v
    }
    private fun initRv(discover: List<DiscoverResultsItem>) {
        discoverAdapter = DiscoverAdapter(discover)
        discoverRv?.adapter = discoverAdapter

    }
}
