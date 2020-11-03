package com.qoolqas.moviedb.model.genrestatic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



data class GenreStaticResponse(
    val data: List<GenreStaticData>? = null
)

@Parcelize
data class GenreStaticData(
    val image: Int? = null,
    val name: String? = null,
    val id: Int? = null
): Parcelable

