//package com.qoolqas.moviedb.database
//
//import android.database.Cursor
//import android.net.Uri
//import android.provider.BaseColumns
//
//class FavoriteContract {
//    object MovieColumns : BaseColumns {
//        const val TABLE_MOVIE = "movie"
//        const val AUTHORITY = "com.qoolqas.moviecataloguesqlfinal"
//        const val SCHEME = "content"
//        const val ID = "id"
//        const val TITLE = "title"
//        const val OVERVIEW = "overview"
//        const val RELEASE = "release_date"
//        const val USER_RATING = "vote_average"
//        const val POSTER_PATH = "posterpath"
//        val CONTENT_URI = Uri.Builder()
//            .scheme(SCHEME)
//            .authority(AUTHORITY)
//            .appendPath(TABLE_MOVIE)
//            .build()
//    }
//
//    object TvColumns : BaseColumns {
//        var TABLE_TV = "tv"
//        const val TVID = "id"
//        const val TITLE_TV = "title"
//        const val OVERVIEW_TV = "overview"
//        const val RELEASE_TV = "first_air_date"
//        const val USER_RATING_TV = "vote_average"
//        const val POSTER_PATH_TV = "posterpath"
//    }
//
//    fun getColumn(cursor: Cursor, column: String?): String? {
//        return cursor.getString(cursor.getColumnIndex(column))
//    }
//}