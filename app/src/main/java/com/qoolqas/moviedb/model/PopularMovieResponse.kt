package com.qoolqas.moviedb.model

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val result: ArrayList<PopularMovieItem>
)