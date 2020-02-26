package com.qoolqas.moviedb.model.genre

import com.google.gson.annotations.SerializedName

data class GenreMovieResponse(

	@field:SerializedName("genres")
	val genres: List<GenresItem>? = null
)