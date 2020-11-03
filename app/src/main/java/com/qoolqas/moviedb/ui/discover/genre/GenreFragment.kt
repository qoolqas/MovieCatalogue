package com.qoolqas.moviedb.ui.discover.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.genrestatic.GenreStaticData


class GenreFragment : Fragment() {
    private val genreData = listOf(
        GenreStaticData(R.drawable.exposter, "Action",28),
        GenreStaticData(R.drawable.exposter, "Adventure",12),
        GenreStaticData(R.drawable.exposter, "Animation",16),
        GenreStaticData(R.drawable.exposter, "Comedy",35),
        GenreStaticData(R.drawable.exposter, "Crime",80),
        GenreStaticData(R.drawable.exposter, "Documentary",99),
        GenreStaticData(R.drawable.exposter, "Drama",18),
        GenreStaticData(R.drawable.exposter, "Family",10751),
        GenreStaticData(R.drawable.exposter, "Fantasy",14),
        GenreStaticData(R.drawable.exposter, "History",36),
        GenreStaticData(R.drawable.exposter, "Horror",27),
        GenreStaticData(R.drawable.exposter, "Music",10402),
        GenreStaticData(R.drawable.exposter, "Mystery",9648),
        GenreStaticData(R.drawable.exposter, "Romance",10749),
        GenreStaticData(R.drawable.exposter, "Science Fiction",878),
        GenreStaticData(R.drawable.exposter, "TV Movie",10770),
        GenreStaticData(R.drawable.exposter, "Thriller",53),
        GenreStaticData(R.drawable.exposter, "War",10752),
        GenreStaticData(R.drawable.exposter, "Western",37)
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}