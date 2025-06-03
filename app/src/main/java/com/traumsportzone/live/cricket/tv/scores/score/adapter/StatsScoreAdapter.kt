package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemStatsLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.HighestScore

class StatsScoreAdapter(): ListAdapter<HighestScore, StatsScoreAdapter.ViewHolder>(DiffCallback){

    inner class ViewHolder(private val binding: ItemStatsLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HighestScore){
            binding.apply {
                modelDataHighestScore = item
                txtWicket.text = item.HS
                txtName.text = item.Batter
                txtOvers.text = item.Balls
                txtAvg.text = item.Vs
                txtMatches.text = item.SR

                executePendingBindings()
            }
        }
    }
    object DiffCallback : DiffUtil.ItemCallback<HighestScore>() {
        override fun areItemsTheSame(
            oldItem: HighestScore,
            newItem: HighestScore
        ): Boolean {
            return oldItem.HS == newItem.HS
        }

        override fun areContentsTheSame(
            oldItem: HighestScore,
            newItem: HighestScore
        ): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsScoreAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatsLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatsScoreAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}