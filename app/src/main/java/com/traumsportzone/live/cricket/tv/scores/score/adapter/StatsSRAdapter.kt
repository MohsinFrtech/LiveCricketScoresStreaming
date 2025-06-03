package com.traumsportzone.live.cricket.tv.scores.score.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.traumsportzone.live.cricket.tv.scores.databinding.ItemStatsLayoutBinding
import com.traumsportzone.live.cricket.tv.scores.score.model.HighestStrrikeRate

class StatsSRAdapter(): ListAdapter<HighestStrrikeRate, StatsSRAdapter.ViewHolder>(DiffCallback) {

    inner class ViewHolder(private val binding: ItemStatsLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: HighestStrrikeRate){
            binding.apply {
                modelDataHighestSr = item
                if (item.SR == ""){
                    txtWicket.text = "----"
                }else{
                    txtWicket.text = item.SR
                }
                txtName.text = item.Batter
                txtOvers.text = item.M
                txtAvg.text = item.I
                txtMatches.text = item.R

                executePendingBindings()
            }
        }
    }
    object DiffCallback : DiffUtil.ItemCallback<HighestStrrikeRate>() {
        override fun areItemsTheSame(
            oldItem: HighestStrrikeRate,
            newItem: HighestStrrikeRate
        ): Boolean {
            return oldItem.SR == newItem.SR
        }

        override fun areContentsTheSame(
            oldItem: HighestStrrikeRate,
            newItem: HighestStrrikeRate
        ): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsSRAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStatsLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatsSRAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}