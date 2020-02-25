package com.qoolqas.moviedb.model

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("result")
    val result: ArrayList<PopularMovieItem>
)