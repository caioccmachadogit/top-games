package com.topgames.ccouto.topgames.utils

import android.content.Context
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by ccouto on 19/02/2019.
 */
object ImageUtil {

    fun loadImage(posterPath: String, context : Context, isCrop:Boolean): DrawableRequestBuilder<String> {
        return if(isCrop)
            Glide
                    .with(context)
                    .load(posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                    .centerCrop()
                    .crossFade()
        else
            Glide
                    .with(context)
                    .load(posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                    .crossFade()
    }
}