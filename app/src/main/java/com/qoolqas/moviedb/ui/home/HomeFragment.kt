package com.qoolqas.moviedb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.nowplaying.NowPlayingResultsItem
import com.qoolqas.moviedb.model.popular.PopularResultsItem
import com.qoolqas.moviedb.ui.home.adapter.NowPlayingAdapter
import com.qoolqas.moviedb.ui.home.adapter.PopularAdapter
import com.qoolqas.moviedb.ui.home.viewmodel.NowPlayingViewModel
import com.qoolqas.moviedb.ui.home.viewmodel.PopularViewModel
import com.qoolqas.moviedb.utils.LinePagerIndicatorDecoration
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var popularViewModel: PopularViewModel
    private lateinit var nowPlayingViewModel: NowPlayingViewModel
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var nowPlayingAdapter: NowPlayingAdapter
    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    private var linearLayoutManagerPager: LinearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//    private var popularRv: RecyclerView? = null
//    private var popularPb: ProgressBar? = null
//    private var popularDivider : View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        popularRv = v.findViewById(R.id.popularRv)
//        popularPb = v.findViewById(R.id.popularPb)
//        popularDivider = v.findViewById(R.id.popularDivider)
//        popularRv?.setHasFixedSize(true)
//        popularRv?.layoutManager = linearLayoutManager
//        setHasOptionsMenu(true)
//
//        popularPb?.visibility = View.VISIBLE
//        popularDivider?.visibility = View.GONE
//
//        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel::class.java)
//        popularViewModel.init(1)
//        popularViewModel.livePopular().observe(viewLifecycleOwner, Observer { popular ->
//            initRv(popular)
//            popularPb?.visibility = View.GONE
//            popularDivider?.visibility = View.VISIBLE
//
//        })

        return inflater.inflate(R.layout.fragment_home, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularRv.setHasFixedSize(true)
        popularRv.layoutManager = linearLayoutManager
        setHasOptionsMenu(true)

        popularPb.visibility = View.VISIBLE
        popularDivider.visibility = View.GONE

        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel::class.java)
        popularViewModel.init(1)
        popularViewModel.livePopular().observe(viewLifecycleOwner, Observer { popular ->
            initPopular(popular)
            popularPb.visibility = View.GONE
            popularDivider.visibility = View.VISIBLE

        })

        rvPager.apply {
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(rvPager)
//            rvPager.addItemDecoration(LinePagerIndicatorDecoration())
            rvPager.layoutManager = linearLayoutManagerPager
        }
        nowPlayingViewModel = ViewModelProviders.of(this).get(NowPlayingViewModel::class.java)
        nowPlayingViewModel.init(1)
        nowPlayingViewModel.liveNowPlaying().observe(viewLifecycleOwner, Observer { nowPlaying ->
            initNowPlaying(nowPlaying)
            popularPb.visibility = View.GONE
            popularDivider.visibility = View.VISIBLE

        })
    }


    private fun initNowPlaying(nowPlaying: List<NowPlayingResultsItem>) {
        nowPlayingAdapter = NowPlayingAdapter(nowPlaying)
        rvPager.adapter = nowPlayingAdapter

    }
    private fun initPopular(popular: List<PopularResultsItem>) {
        popularAdapter = PopularAdapter(popular)
        popularRv.adapter = popularAdapter

    }
}
