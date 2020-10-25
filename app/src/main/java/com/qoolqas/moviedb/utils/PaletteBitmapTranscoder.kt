//package com.qoolqas.moviedb.utils
//
//import android.content.Context
//import android.graphics.Bitmap
//import androidx.palette.graphics.Palette
//import com.bumptech.glide.Glide
//import com.bumptech.glide.load.Options
//import com.bumptech.glide.load.engine.Resource
//import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
//import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
//
//
//class PaletteBitmapTranscoder(context: Context) :
//    ResourceTranscoder<Bitmap?, PaletteBitmap?> {
//    private val bitmapPool: BitmapPool
//    fun transcode(toTranscode: Resource<Bitmap?>): Resource<PaletteBitmap> {
//        val bitmap: Bitmap = toTranscode.get()
//        val palette = Palette.Builder(bitmap).generate()
//        val result = PaletteBitmap(bitmap, palette)
//        return PaletteBitmapResource(result, bitmapPool)
//    }
//
//    val id: String
//        get() = PaletteBitmapTranscoder::class.java.name
//
//    init {
//        bitmapPool = Glide.get(context).bitmapPool
//    }
//
//    override fun transcode(
//        toTranscode: Resource<Bitmap?>,
//        options: Options
//    ): Resource<PaletteBitmap?>? {
//        TODO("Not yet implemented")
//    }
//}