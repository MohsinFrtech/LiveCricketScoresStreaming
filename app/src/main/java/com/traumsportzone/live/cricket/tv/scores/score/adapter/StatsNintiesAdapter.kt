package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemStatsLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.MostNinties

class StatsNintiesAdapter () : ListAdapter<MostNinties, StatsNintiesAdapter.ViewHolder>(DiffCallback) {
    inner class ViewHolder(private val binding: ItemStatsLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MostNinties){
            binding.apply {
                modelDataMostNinties = item
                txtWicket.text = item.R
                txtName.text = item.Batter
                txtOvers.text = item.I
                txtAvg.text = item.ninty
                txtMatches.text = item.M

                executePendingBindings()
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<MostNinties>() {
        override fun areItemsTheSame(
            oldItem: MostNinties,
            newItem: MostNinties
        ): Boolean {
            return oldItem.R == newItem.R
        }

        override fun areContentsTheSame(
            oldItem: MostNinties,
            newItem: MostNinties
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