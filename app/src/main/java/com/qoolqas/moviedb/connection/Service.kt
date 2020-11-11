package com.qoolqas.moviedb.connection

import com.qoolqas.moviedb.model.credits.CreditsResponse
import com.qoolqas.moviedb.model.details.DetailsMovieResponse
import com.qoolqas.moviedb.model.discover.DiscoverMovieResponse
import com.qoolqas.moviedb.model.nowplaying.NowPlayingResponse
import com.qoolqas.moviedb.model.popular.PopularMovieResponse
import com.qoolqas.moviedb.model.similiar.SimiliarResponse
import com.qoolqas.moviedb.model.tv.TvPopularResponse
import com.qoolqas.moviedb.model.tvdetails.TvDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    //region Now Playing
    @GET("movie/now_playing")
    fun getNowPlaying(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<NowPlayingResponse>

    //endregion

    //region Popular
    @GET("movie/popular")
    fun getPopular(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<PopularMovieResponse>

    @GET("tv/popular")
    fun getTvPopular(
        @Query("api_key") api: String,
        @Query("page") page: Int,
        @Query("language") language: String
    ): Call<TvPopularResponse>


    //endregion

    //region Discover
    @GET("discover/movie")
    fun getDiscover(
        @Query("api_key") api: String,
        @Query("page") page: Int
    ): Call<DiscoverMovieResponse>

    @GET("discover/movie")
    fun getSearchByGenre(
        @Query("api_key") api: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        @Query("with_genres") genre: String

    ): Call<DiscoverMovieResponse>

    @GET("search/movie")
    fun getSearchMovie(
        @Query("api_key") api: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int

    ): Call<DiscoverMovieResponse>
    //endregion

    //region Details
    @GET("movie/{movie_id}")
    fun getDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") api: String,
        @Query("language") language: String

    ): Call<DetailsMovieResponse>
    @GET("tv/{tv_id}")
    fun getDetailsTv(
        @Path("tv_id") id: Int,
        @Query("api_key") api: String,
        @Query("language") language: String

    ): Call<TvDetailResponse>
    //endregion

    //region Similiar
    @GET("movie/{movie_id}/similar")
    fun getSimiliar(
        @Path("movie_id")id : Int,
        @Query("api_key") api: String,
        @Query("page") page: Int

    ): Call<SimiliarResponse>
    //endregion

    //region Credits
    @GET("movie/{movie_id}/credits")
    fun getCredits(
        @Path("movie_id")id : Int,
        @Query("api_key") api: String,
        @Query("language") language: String
    ): Call<CreditsResponse>
    //endregion
}