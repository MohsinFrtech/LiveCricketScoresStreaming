package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.R.attr.content
import android.R.attr.text
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.R
import com.traumsportzone.live.cricket.tv.scores.databinding.BowlerItemBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.CommentryLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.OverItemLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.ScoreCardItemBinding
import com.traumsportzone.live.cricket.tv.scores.databinding.SquadLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.BatsmenData
import com.traumsportzone.live.cricket.tv.scores.score.model.BowlersData
import com.traumsportzone.live.cricket.tv.scores.score.model.CommentaryList
import com.traumsportzone.live.cricket.tv.scores.score.model.PlayerDetails
import com.traumsportzone.live.cricket.tv.scores.score.model.SquadModel


class BowlerStatsAdapter(val context: Context) :
    ListAdapter<BowlersData, BowlerStatsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: BowlerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(batData: BowlersData) {
            binding?.teamTxt2?.text = batData.bowlName.toString()
//            binding?.batOutStatus2?.text = batData.outDesc?.toString()
            binding?.team12?.text = batData.overs?.toString()
            binding?.team22?.text = batData.runs?.toString()
            binding?.team32?.text = batData?.wickets?.toString()
            binding?.team42?.text = batData?.wides?.toString()
            binding?.teamTotal2?.text = batData?.economy?.toString()
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<BowlersData>() {
        override fun areItemsTheSame(oldItem: BowlersData, newItem: BowlersData): Boolean {
            return oldItem.bowlerId == newItem.bowlerId
        }

        override fun areContentsTheSame(oldItem: BowlersData, newItem: BowlersData): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(BowlerItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val batData = currentList[position]
        holder.bind(batData)
    }


}