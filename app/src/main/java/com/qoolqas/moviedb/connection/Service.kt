package com.qoolqas.moviedb.connection

import com.qoolqas.moviedb.model.genre.GenreMovieResponse
import com.qoolqas.moviedb.model.popular.PopularMovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("movie/popular")
    fun getPopular(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ):Call<PopularMovieResponse>

    @GET("genre/movie/list")
    fun getGenre(
        @Query("api_key") api: String
    ):Call<GenreMovieResponse>
}