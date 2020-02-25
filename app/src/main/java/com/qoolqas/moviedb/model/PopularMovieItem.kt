package com.qoolqas.moviedb.model

import com.google.gson.annotations.SerializedName

data class PopularMovieItem(
    @SerializedName("poster_path")
    val poster_path: String,
    @SerializedName("title", alternate = ["name"])
    val title: String
)
