package com.traumsportzone.live.cricket.tv.scores.streaming.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.traumsportzone.live.cricket.tv.scores.R

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(imageView)

    }
}