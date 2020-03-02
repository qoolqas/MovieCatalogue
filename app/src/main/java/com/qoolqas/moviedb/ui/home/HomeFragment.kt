package com.qoolqas.moviedb.ui.home

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
import com.qoolqas.moviedb.model.popular.PopularResultsItem

class HomeFragment : Fragment() {

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var adapter: PopularAdapter
    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    private var popularRv: RecyclerView? = null
    private var popularPb: ProgressBar? = null
    private var popularDivider : View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        popularRv = v.findViewById(R.id.popularRv)
        popularPb = v.findViewById(R.id.popularPb)
        popularDivider = v.findViewById(R.id.popularDivider)
        popularRv?.setHasFixedSize(true)
        popularRv?.layoutManager = linearLayoutManager
        setHasOptionsMenu(true)

        popularPb?.visibility = View.GONE
        popularDivider?.visibility = View.GONE

        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel::class.java)
        popularViewModel.init(1)
        popularViewModel.livePopular().observe(viewLifecycleOwner, Observer { popular ->
            initRv(popular)
            popularPb?.visibility = View.GONE
            popularDivider?.visibility = View.VISIBLE


        })

        return v
    }

    private fun initRv(popular: List<PopularResultsItem>) {
        adapter = PopularAdapter(popular)
        popularRv?.adapter = adapter

    }
}
