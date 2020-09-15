package com.qoolqas.moviedb.utils

import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.util.Util


class PaletteBitmapResource(
    private val paletteBitmap: PaletteBitmap,
    private val bitmapPool: BitmapPool
) :
    Resource<PaletteBitmap?> {
    override fun get(): PaletteBitmap {
        return paletteBitmap
    }

    val size: Int = Util.getBitmapByteSize(paletteBitmap.bitmap)

    override fun recycle() {
        if (bitmapPool.put(paletteBitmap.bitmap)) {
            paletteBitmap.bitmap.recycle()
        }
    }

}