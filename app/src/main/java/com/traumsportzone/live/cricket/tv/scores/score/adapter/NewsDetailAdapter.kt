package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.R.attr.text
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.CommentryLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.NewsItemBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.SquadLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.CommentaryList
import com.traumsportzone.live.cricket.tv.scores.score.model.Content
import com.traumsportzone.live.cricket.tv.scores.score.model.ContentInside
import com.traumsportzone.live.cricket.tv.scores.score.model.PlayerDetails
import com.traumsportzone.live.cricket.tv.scores.score.model.SquadModel


class NewsDetailAdapter() :
    ListAdapter<ContentInside, NewsDetailAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contentc: ContentInside) {

           if (contentc.content?.contentType.equals("text",true))
           {
               binding?.newDetail?.text = contentc.content?.contentValue.toString()
           }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<ContentInside>() {
        override fun areItemsTheSame(oldItem: ContentInside, newItem: ContentInside): Boolean {
            return oldItem.content?.contentValue == newItem.content?.contentValue
        }

        override fun areContentsTheSame(oldItem: ContentInside, newItem: ContentInside): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(NewsItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val league = currentList[position]
        holder.bind(league)


    }


}