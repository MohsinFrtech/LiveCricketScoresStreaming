package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemStatsLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.MostWickets

class StatsAdapter(): ListAdapter<MostWickets, StatsAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemStatsLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MostWickets){
            binding.apply {
                modelData = item
                txtWicket.text = item.W
                txtName.text = item.Bowler
                txtOvers.text = item.O
                txtAvg.text = item.Avg
                txtMatches.text = item.M

                executePendingBindings()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<MostWickets>() {
        override fun areItemsTheSame(
            oldItem: MostWickets,
            newItem: MostWickets
        ): Boolean {
            return oldItem.W == newItem.W
        }

        override fun areContentsTheSame(
            oldItem: MostWickets,
            newItem: MostWickets
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatsLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}