package com.topgames.ccouto.topgames.utils

import android.content.Context
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by ccouto on 15/11/2017.
 */
object ImageUtil {

    fun loadImage(posterPath: String, context : Context, isCrop:Boolean): DrawableRequestBuilder<String> {
        if(isCrop)
            return Glide
                .with(context)
                .load(posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .crossFade()
        else
            return Glide
                    .with(context)
                    .load(posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                    .crossFade()
    }
}