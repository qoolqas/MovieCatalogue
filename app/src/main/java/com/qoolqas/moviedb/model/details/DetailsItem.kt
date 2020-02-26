package com.qoolqas.moviedb.model.details

import com.google.gson.annotations.SerializedName


data class DetailsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)