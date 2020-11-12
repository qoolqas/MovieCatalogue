package com.qoolqas.moviedb.ui.discover.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.genrestatic.StaticData
import kotlinx.android.synthetic.main.fragment_genre.*


class GenreFragment : Fragment() {
    private val genreData = listOf(
        StaticData(R.drawable.exposter, "Action",28),
        StaticData(R.drawable.exbackdrop, "Adventure",12),
        StaticData(R.drawable.exposter, "Animation",16),
        StaticData(R.drawable.exbackdrop, "Comedy",35),
        StaticData(R.drawable.exposter, "Crime",80),
        StaticData(R.drawable.exbackdrop, "Documentary",99),
        StaticData(R.drawable.exposter, "Drama",18),  
        StaticData(R.drawable.exbackdrop, "Family",10751),
        StaticData(R.drawable.exposter, "Fantasy",14),
        StaticData(R.drawable.exbackdrop, "History",36),
        StaticData(R.drawable.exposter, "Horror",27),
        StaticData(R.drawable.exposter, "Music",10402),
        StaticData(R.drawable.exposter, "Mystery",9648),
        StaticData(R.drawable.exposter, "Romance",10749),
        StaticData(R.drawable.exposter, "Science Fiction",878),
        StaticData(R.drawable.exposter, "TV Movie",10770),
        StaticData(R.drawable.exposter, "Thriller",53),
        StaticData(R.drawable.exposter, "War",10752),
        StaticData(R.drawable.exposter, "Western",37)
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
        genreRv.apply {
            genreRv.layoutManager = GridLayoutManager(context, 2)
            adapter = GenreAdapter(genreData)
        }
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        activity!!.onBackPressedDispatcher.addCallback(this){
//            findNavController().navigateUp()
//        }
//    }
}