package com.qoolqas.moviedb.model.similiar

import com.google.gson.annotations.SerializedName

data class SimiliarResponse(

    @field:SerializedName("page")
	val page: Int? = null,

    @field:SerializedName("total_pages")
	val totalPages: Int? = null,

    @field:SerializedName("results")
	val results: List<SimiliarResultsItem>? = null,

    @field:SerializedName("total_results")
	val totalResults: Int? = null
)