//package com.qoolqas.moviedb.database
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.database.SQLException
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import com.qoolqas.moviedb.database.FavoriteContract.TvColumns.TITLE_TV
//import java.util.*
//
//class FavoriteHelper {
//    private val DATABASE_TABLE: String = TABLE_MOVIE
//    private val DATABASE_TABLE_TV: String = TABLE_TV
//    private var favoriteDbHelper: FavoriteDbHelper? = null
//    private var INSTANCE: FavoriteHelper? = null
//
//    private var database: SQLiteDatabase? = null
//
//    fun FavoriteHelper(context: Context?) {
//        favoriteDbHelper = FavoriteDbHelper(context)
//    }
//
//    fun getInstance(context: Context?): FavoriteHelper? {
//        if (INSTANCE == null) {
//            synchronized(SQLiteOpenHelper::class.java) {
//                if (INSTANCE == null) {
//                    INSTANCE = FavoriteHelper(context)
//                }
//            }
//        }
//        return INSTANCE
//    }
//
//    @Throws(SQLException::class)
//    fun open() {
//        database = favoriteDbHelper!!.writableDatabase
//    }
//
//    fun close() {
//        favoriteDbHelper!!.close()
//        if (database!!.isOpen) database!!.close()
//    }
//
//    fun getAllMovies(): ArrayList<Movie>? {
//        val arrayList: ArrayList<Movie> = ArrayList<Movie>()
//        val cursor = database!!.query(
//            DATABASE_TABLE, null,
//            null,
//            null,
//            null,
//            null, ID.toString() + " ASC",
//            null
//        )
//        cursor.moveToFirst()
//        var movie: Movie
//        if (cursor.count > 0) {
//            do {
//                movie = Movie(cursor)
//                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)))
//                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TV)))
//                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)))
//                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)))
//                movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(USER_RATING)))
//                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)))
//                arrayList.add(movie)
//                cursor.moveToNext()
//            } while (!cursor.isAfterLast)
//        }
//        cursor.close()
//        return arrayList
//    }
//
//    fun insertMovie(movie: Movie): Long {
//        val args = ContentValues()
//        args.put(ID, movie.getId())
//        args.put(TITLE, movie.getTitle())
//        args.put(OVERVIEW, movie.getOverview())
//        args.put(RELEASE, movie.getReleaseDate())
//        args.put(USER_RATING, movie.getVoteAverage())
//        args.put(POSTER_PATH, movie.getPosterPath())
//        return database!!.insert(DATABASE_TABLE, null, args)
//    }
//
//    fun deleteMovie(id: Int): Int {
//        return database!!.delete(TABLE_MOVIE, ID.toString() + " = '" + id + "'", null)
//    }
//
//    fun getAllTv(): ArrayList<TvShow>? {
//        val arrayList: ArrayList<TvShow> = ArrayList<TvShow>()
//        val cursor = database!!.query(
//            DATABASE_TABLE_TV, null,
//            null,
//            null,
//            null,
//            null, TVID.toString() + " ASC",
//            null
//        )
//        cursor.moveToFirst()
//        var tvShow: TvShow
//        if (cursor.count > 0) {
//            do {
//                tvShow = TvShow()
//                tvShow.setId(cursor.getInt(cursor.getColumnIndexOrThrow(TVID)))
//                tvShow.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_TV)))
//                tvShow.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW_TV)))
//                tvShow.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_TV)))
//                tvShow.setVoteAverage(cursor.getDouble(cursor.getColumnIndexOrThrow(USER_RATING_TV)))
//                tvShow.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH_TV)))
//                arrayList.add(tvShow)
//                cursor.moveToNext()
//            } while (!cursor.isAfterLast)
//        }
//        cursor.close()
//        return arrayList
//    }
//
//    fun insertTv(tvshow: TvShow): Long {
//        val args = ContentValues()
//        args.put(TVID, tvshow.getId())
//        args.put(TITLE_TV, tvshow.getTitle())
//        args.put(OVERVIEW_TV, tvshow.getOverview())
//        args.put(RELEASE_TV, tvshow.getReleaseDate())
//        args.put(USER_RATING_TV, tvshow.getVoteAverage())
//        args.put(POSTER_PATH_TV, tvshow.getPosterPath())
//        return database!!.insert(DATABASE_TABLE_TV, null, args)
//    }
//
//    fun deleteTv(id: Int): Int {
//        return database!!.delete(TABLE_TV, TVID.toString() + " = '" + id + "'", null)
//    }
//
//    fun MovieProvider(): Cursor? {
//        return database!!.query(DATABASE_TABLE, null, null, null, null, null, null)
//    }
//
//    fun TvProvider(): Cursor? {
//        return database!!.query(DATABASE_TABLE_TV, null, null, null, null, null, null)
//    }
//
//    fun deleteProvider(id: String): Int {
//        return database!!.delete(
//            DATABASE_TABLE,
//            FavoriteContract.MovieColumns._ID.toString() + "=?",
//            arrayOf(id)
//        )
//    }
//
//    fun deleteProviderTv(idt: String): Int {
//        return database!!.delete(
//            DATABASE_TABLE_TV,
//            FavoriteContract.TvColumns._ID.toString() + "=?",
//            arrayOf(idt)
//        )
//    }
//}
