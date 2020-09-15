package com.qoolqas.moviedb.utils

import android.graphics.Bitmap
import androidx.palette.graphics.Palette


class PaletteBitmap(val bitmap: Bitmap, palette: Palette) {
    val palette: Palette

    init {
        this.palette = palette
    }
}