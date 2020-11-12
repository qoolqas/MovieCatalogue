//package com.qoolqas.moviedb.database
//
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.qoolqas.moviedb.database.FavoriteContract.MovieColumns.TABLE_MOVIE
//import com.qoolqas.moviedb.database.FavoriteContract.TvColumns.TABLE_TV
//import java.lang.String
//
//class FavoriteDbHelper: SQLiteOpenHelper {
//    private val MOVIE_DATABASE_NAME = "favoritemovie"
//
//    private val MOVIE_DATABASE_VERSION = 1
//
//    private val SQL_CREATE_TABLE_MOVIE = String.format(
//        "CREATE TABLE %s" +
//                " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
//                " %s INTEGER NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE)",
//        TABLE_MOVIE,
//        FavoriteContract.MovieColumns._ID,
//        FavoriteContract.MovieColumns.ID,
//        FavoriteContract.MovieColumns.TITLE,
//        FavoriteContract.MovieColumns.OVERVIEW,
//        FavoriteContract.MovieColumns.RELEASE,
//        FavoriteContract.MovieColumns.USER_RATING,
//        FavoriteContract.MovieColumns.POSTER_PATH
//    )
//    private val SQL_CREATE_TV_TABLE = String.format(
//        "CREATE TABLE %s" +
//                " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
//                " %s INTEGER NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE," +
//                " %s TEXT NOT NULL UNIQUE)",
//        TABLE_TV,
//        FavoriteContract.TvColumns._ID,
//        FavoriteContract.TvColumns.TVID,
//        FavoriteContract.TvColumns.TITLE_TV,
//        FavoriteContract.TvColumns.RELEASE_TV,
//        FavoriteContract.TvColumns.USER_RATING_TV,
//        FavoriteContract.TvColumns.OVERVIEW_TV,
//        FavoriteContract.TvColumns.POSTER_PATH_TV
//    )
//
//    fun FavoriteDbHelper(context: Context?) {
//        super(context, MOVIE_DATABASE_NAME, null, MOVIE_DATABASE_VERSION)
//    }
//
//    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
//        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE)
//        sqLiteDatabase.execSQL(SQL_CREATE_TV_TABLE)
//    }
//
//    override fun onUpgrade(
//        sqLiteDatabase: SQLiteDatabase,
//        oldVersion: Int,
//        newVersion: Int
//    ) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TABLE_TV")
//        onCreate(sqLiteDatabase)
//    }
//}