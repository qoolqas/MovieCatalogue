package com.qoolqas.moviedb.ui.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.ui.discover.genre.SearchGenreFragmentArgs
import java.util.*

class SearchMovieFragment : Fragment() {
    private var language = Locale.getDefault().toLanguageTag()
    var page: Int = 1
    lateinit var query : String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getString("searchData")
        Log.d("Args", args)
    }
}