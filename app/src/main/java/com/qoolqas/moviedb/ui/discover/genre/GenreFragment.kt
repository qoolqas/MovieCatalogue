package com.qoolqas.moviedb.ui.discover.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.qoolqas.moviedb.R
import com.qoolqas.moviedb.model.genrestatic.GenreStaticData
import kotlinx.android.synthetic.main.fragment_genre.*


class GenreFragment : Fragment() {
    private val genreData = listOf(
        GenreStaticData(R.drawable.exposter, "Action",28),
        GenreStaticData(R.drawable.exbackdrop, "Adventure",12),
        GenreStaticData(R.drawable.exposter, "Animation",16),
        GenreStaticData(R.drawable.exbackdrop, "Comedy",35),
        GenreStaticData(R.drawable.exposter, "Crime",80),
        GenreStaticData(R.drawable.exbackdrop, "Documentary",99),
        GenreStaticData(R.drawable.exposter, "Drama",18),
        GenreStaticData(R.drawable.exbackdrop, "Family",10751),
        GenreStaticData(R.drawable.exposter, "Fantasy",14),
        GenreStaticData(R.drawable.exbackdrop, "History",36),
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
        genreRv.apply {
            genreRv.layoutManager = GridLayoutManager(context, 2)
            adapter = GenreAdapter(genreData)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.onBackPressedDispatcher.addCallback(this){
            findNavController().navigateUp()
        }
    }
    //    override fun onBackPressed(){
//        Navigation.createNavigateOnClickListener(R.id.action_nav_genre_to_nav_home)
//    }
}