//package com.qoolqas.moviedb
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.qoolqas.moviedb.model.popular.PopularResultsItem
//
//import com.qoolqas.moviedb.ui.home.PopularViewmodel
//import com.qoolqas.moviedb.ui.home.PopularAdapter
//import kotlinx.android.synthetic.main.activity_second.*
//import kotlinx.android.synthetic.main.fragment_home.*
//
//class SecondActivity : AppCompatActivity() {
//
//    private lateinit var viewmodel: PopularViewmodel
//    private lateinit var adapter: PopularAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_second)
//
//        rv.setHasFixedSize(true)
//        rv.layoutManager = LinearLayoutManager(this)
//
//        viewmodel = ViewModelProviders.of(this).get(PopularViewmodel::class.java)
//        viewmodel.init(1)
//        viewmodel.livePopular().observe(this, Observer { popular ->
//            initRv(popular)
//        })
//    }
//
//    private fun initRv(popular: List<PopularResultsItem>) {
//        adapter = PopularAdapter(popular)
//        rv.adapter = adapter
//
//    }
//}
