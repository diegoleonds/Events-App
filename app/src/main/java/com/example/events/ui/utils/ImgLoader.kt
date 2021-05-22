package com.example.events.ui.utils

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.events.R

class ImgLoader(
    private val glide: RequestManager,
) {
    fun loadImage(imgUrl: String, imgError: Int = R.drawable.error_image, imgView: ImageView){
        glide
            .load(imgUrl)
            .error(imgError)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imgView);
    }
}