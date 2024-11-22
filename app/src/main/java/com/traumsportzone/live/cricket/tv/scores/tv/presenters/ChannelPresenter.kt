package com.traumsportzone.live.cricket.tv.scores.tv.presenters


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.TvCustomLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.TvcustomChannelBinding
import com.traumsportzone.live.cricket.tv.scores.streaming.models.Channel


class ChannelPresenter(private val context: Context) : Presenter() {

    var binding: TvcustomChannelBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.tvcustom_channel, parent, false)
        binding = DataBindingUtil.bind(view)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        val movie = item as Channel
        binding?.channelName?.text = movie.name
        binding?.channelImage?.let {
            Glide.with(context)
                .load(movie.image_url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                )
                .into(it)
        }

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
        // Remove references to images so that the garbage collector can free up memory

    }
}