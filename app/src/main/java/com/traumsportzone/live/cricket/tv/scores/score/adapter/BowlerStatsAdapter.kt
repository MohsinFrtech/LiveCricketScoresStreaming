package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.BowlerItemBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.Bowler


class BowlerStatsAdapter(val context: Context) :
    ListAdapter<Bowler, BowlerStatsAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: BowlerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(batData: Bowler) {
            binding?.teamTxt2?.text = batData.name.toString()
//            binding?.batOutStatus2?.text = batData.outDesc?.toString()
            binding?.team12?.text = batData.overs?.toString()
            binding?.team22?.text = batData.runs?.toString()
            binding?.team32?.text = batData?.wickets?.toString()
            binding?.team42?.text = batData?.balls?.toString()
            binding?.teamTotal2?.text = batData?.economy?.toString()
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Bowler>() {
        override fun areItemsTheSame(oldItem: Bowler, newItem: Bowler): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bowler, newItem: Bowler): Boolean {
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