package com.qoolqas.moviedb.connection

import com.qoolqas.moviedb.model.details.DetailsMovieResponse
import com.qoolqas.moviedb.model.discover.DiscoverMovieResponse
import com.qoolqas.moviedb.model.genre.GenreMovieResponse
import com.qoolqas.moviedb.model.popular.PopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    //region Popular
    @GET("movie/popular")
    fun getPopular(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<PopularMovieResponse>

    //endregion

    //region Discover
    @GET("discover/movie")
    fun getDiscover(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<DiscoverMovieResponse>
    //endregion

    //region Details
    @GET("movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") api: String

    ): Call<DetailsMovieResponse>
    //endregion

    //region Genre
    @GET("genre/movie/list")
    fun getGenre(
        @Query("api_key") api: String
    ): Call<GenreMovieResponse>
    //endregion

}