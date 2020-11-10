package com.qoolqas.moviedb.model.genrestatic

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



data class GenreStaticResponse(
    val data: List<StaticData>? = null
)

@Parcelize
data class StaticData(
    val image: Int? = null,
    val name: String? = null,
    val id: Int? = null,
    val kode: String? = null
): Parcelable

